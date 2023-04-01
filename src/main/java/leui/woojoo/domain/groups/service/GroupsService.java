package leui.woojoo.domain.groups.service;

import leui.woojoo.domain.games.GameUtils;
import leui.woojoo.domain.games.Games;
import leui.woojoo.domain.groups.Groups;
import leui.woojoo.domain.groups.respository.GroupsRepository;
import leui.woojoo.domain.users.Users;
import leui.woojoo.domain.users.dto.UserInList;
import leui.woojoo.domain.users.service.RelationshipService;
import leui.woojoo.domain.users.service.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
                    .intersection(gameIntersection)
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
