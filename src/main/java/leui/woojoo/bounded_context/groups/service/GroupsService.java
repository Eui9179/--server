package leui.woojoo.bounded_context.groups.service;

import leui.woojoo.utils.GameUtils;
import leui.woojoo.bounded_context.games.entity.Games;
import leui.woojoo.bounded_context.groups.entity.Groups;
import leui.woojoo.bounded_context.groups.entity.GroupsRepository;
import leui.woojoo.bounded_context.users.dto.UserInList;
import leui.woojoo.bounded_context.users.entity.Users;
import leui.woojoo.bounded_context.users.service.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class GroupsService {

    private final GroupsRepository groupsRepository;
    private final UsersService usersService;

    public Long save(Groups groups) {
        return groupsRepository.save(groups).getId();
    }

    public List<Groups> findAllByGroupNameDetail(String groupName, String detail1) {
        return detail1.equals("") ? groupsRepository.findAllByGroupName(groupName) :
                groupsRepository.findAllByGroupNameAndDetail1(groupName, detail1);
    }

    @Transactional(readOnly = true)
    public Map<String, List<UserInList>> findUsersByGroup(Long userId, String groupName, String groupDetail) {
        Users userEntity = usersService.findById(userId);
        List<Games> games = userEntity.getGames();
        List<Long> friendIdList = usersService.findMyFriendIdAllById(userEntity.getId());
        List<Long> blocklist = usersService.findBlockListByUsers(userEntity);

        List<Groups> usersInGroup = findAllByGroupNameDetail(groupName, groupDetail);

        List<UserInList> friends = new ArrayList<>();
        List<UserInList> people = new ArrayList<>();

        for (Groups group : usersInGroup) {
            Users other = group.getUsers();
            if (blocklist.contains(other.getId()) || group.getUsers().getId().equals(userId)) continue;

            List<String> gameIntersection = GameUtils.getGameIntersection(userEntity.getGames(), games);

            UserInList userInList = UserInList.builder()
                    .id(other.getId())
                    .name(other.getName())
                    .profileImageName(other.getProfileImageName())
                    .games(gameIntersection)
                    .build();

            if (friendIdList.contains(other.getId())) {
                friends.add(userInList);
            } else {
                people.add(userInList);
            }
        }
        Collections.sort(friends);
        Collections.sort(people);

        return Map.of(
                "friends", friends,
                "people", people
        );
    }

}
