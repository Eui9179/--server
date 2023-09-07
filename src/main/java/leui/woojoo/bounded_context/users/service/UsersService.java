package leui.woojoo.bounded_context.users.service;

import leui.woojoo.DataNotFoundException;
import leui.woojoo.base.utils.FileUtils;
import leui.woojoo.base.utils.GameUtils;
import leui.woojoo.bounded_context.games.dto.Game;
import leui.woojoo.bounded_context.games.entity.Games;
import leui.woojoo.bounded_context.groups.dto.GroupSimple;
import leui.woojoo.bounded_context.groups.entity.Groups;
import leui.woojoo.bounded_context.users.dto.UserDetail;
import leui.woojoo.bounded_context.users.dto.UserInList;
import leui.woojoo.bounded_context.users.dto.UserProfileUpdate;
import leui.woojoo.bounded_context.users.dto.web.UserProfileResponse;
import leui.woojoo.bounded_context.users.dto.web.UserSettingRequest;
import leui.woojoo.bounded_context.users.entity.Users;
import leui.woojoo.bounded_context.users.repository.FriendIds;
import leui.woojoo.bounded_context.users.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class UsersService {
    private final UsersRepository usersRepository;
    private final FileUtils fileUtils;

    @Transactional
    public void asyncFcmToken(Long userId, String fcm) {
        Users users = usersRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("해당 유저가 없습니다. " + userId));
        if (!users.getFcmToken().equals(fcm)) {
            users.asyncFcmToken(fcm);
        }
    }

    public UserDetail findUserDetailById(Long userId) {
        return new UserDetail(usersRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("해당 유저가 없습니다." + userId)));
    }

    public Users findById(Long userId) {
        return usersRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("해당 유저가 없습니다. " + userId));
    }

    @Transactional(readOnly = true)
    public GroupSimple findUserGroupsByUserId(Long userId) {
        Users users = usersRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. " + userId));
        return new GroupSimple(users.getGroups());
    }

    @Transactional
    public boolean updateUserProfile(Long userId, UserProfileUpdate userProfileUpdate) {
        Users user = usersRepository.findById(userId)
                .orElse(null);

        if (user == null) {
            return false;
        }

        if (userProfileUpdate.getName() != null) {
            user.updateUserName(userProfileUpdate.getName());
        }

        if (userProfileUpdate.getProfileImageName() != null) {
            user.updateProfileImageName(userProfileUpdate.getProfileImageName());
        }

        if (userProfileUpdate.getGroupName() != null) {
            Groups userGroup = user.getGroups();
            userGroup.update(userProfileUpdate.getGroupName(), userProfileUpdate.getGroupDetail());
        }
        return true;
    }

    @Transactional(readOnly = true)
    public UserProfileResponse findUserProfileByUserIdAndOtherId(Long userId, Long otherId) {
        Users me = findById(userId);
        List<Long> myFriendIds = findMyFriendIdAllById(me.getId());
        List<Games> myGames = me.getGames();

        boolean isFriend = myFriendIds.contains(otherId);

        Users other = findById(otherId);
        GroupSimple otherGroup = new GroupSimple(other.getGroups());
        List<Game> otherGames = findUserGames(other);
        Set<Users> otherFriends = other.getFriends();

        ArrayList<UserInList> alreadyFriends = new ArrayList<>();
        ArrayList<UserInList> userFriends = new ArrayList<>();

        for (Users otherFriend : otherFriends) {
            if (userId.equals(otherFriend.getId())) continue;

            List<Games> userFriendGames = otherFriend.getGames();
            List<String> intersection = GameUtils.getGameIntersection(myGames, userFriendGames);

            UserInList userInList = UserInList.builder()
                    .id(otherFriend.getId())
                    .name(otherFriend.getName())
                    .profileImageName(otherFriend.getProfileImageName())
                    .games(intersection)
                    .build();
            if (myFriendIds.contains(otherFriend.getId())) {
                alreadyFriends.add(userInList);
            } else {
                userFriends.add(userInList);
            }
        }

        Collections.sort(alreadyFriends);
        Collections.sort(userFriends);

        return UserProfileResponse.builder()
                .userProfile(other.toProfile())
                .isFriend(isFriend)
                .userGroups(new ArrayList<>(Collections.singletonList(otherGroup)))
                .userGames(otherGames)
                .alreadyFriends(alreadyFriends)
                .userFriends(userFriends)
                .build();
    }

    public List<Game> findUserGames(Users user) {
        return user.getGames().stream()
                .map(Game::new)
                .toList();
    }

    public Users findByPhoneNumber(String phoneNumber) {
        return usersRepository.findByPhoneNumber(phoneNumber)
                .orElse(null);
    }

    public List<Long> findMyFriendIdAllById(Long userId) {
        return usersRepository.findFriendIdAllById(userId)
                .stream()
                .map(FriendIds::getFriendId)
                .toList();
    }

    public List<Long> findBlockListByUsers(Users users) {
        return users.getBlocklist()
                .stream()
                .map(Users::getId)
                .toList();
    }

    public List<String> getMyFriendFcmTokenList(Users user) {
        Set<Users> friends = user.getFriends();
        return friends.stream()
                .map(Users::getFcmToken)
                .toList();
    }

    public ResponseEntity<String> updateUserSetting(Long userId, UserSettingRequest request) throws IOException {
        UserDetail userDetail = findUserDetailById(userId);

        UserProfileUpdate userProfileUpdate = new UserProfileUpdate();
        String profileImageName = null;

        if (request.getIsFile().equals("true")) {
            if (request.getFile() != null) {
                if (!userDetail.getProfileImageName().equals("default")) {
                    fileUtils.delete(userDetail.getProfileImageName(), "profile");
                }
                profileImageName = fileUtils.upload(request.getFile(), "profile");
            } else {
                if (!userDetail.getProfileImageName().equals("default.png")) {
                    fileUtils.delete(userDetail.getProfileImageName(), "profile");
                }
            }
            userProfileUpdate.setProfileImageName(profileImageName);
        }
        if (request.getName() != null) {
            userProfileUpdate.setName(request.getName());
        }

        if (request.getIsGroup().equals("true")) {
            userProfileUpdate.setGroupName(request.getGroupName());
            userProfileUpdate.setGroupDetail(request.getGroupDetail1());
        }

        if (!updateUserProfile(userId, userProfileUpdate)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(profileImageName, HttpStatus.OK);
    }

    public Resource getProfileImage(String filename) throws IOException {
        return fileUtils.download(filename, "profile");
    }
}
