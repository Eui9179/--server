package leui.woojoo.bounded_context.users.service;

import leui.woojoo.bounded_context.users.dto.UserInList;
import leui.woojoo.bounded_context.users.entity.Users;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class RelationshipService {
    private final UsersService usersService;

    @Transactional
    public void syncFriends(Long userId, List<String> phoneNumberList) {
        Users user = usersService.findById(userId);
        for (String phoneNumber : phoneNumberList) {
            Users other = usersService.findByPhoneNumber(phoneNumber);
            if (other == null) continue;
            user.addFriend(other);
        }
    }

    public List<UserInList> findFriendInListAll(Long userId) {
        Users user = usersService.findById(userId);
        return user.getFriends()
                .stream()
                .map(UserInList::new)
                .toList();
    }

    @Transactional
    public UserInList insertFriend(Long userId, Long friendId) {
        Users user = usersService.findById(userId);
        Users friend = usersService.findById(friendId);
        user.getFriends().add(friend);
        return new UserInList(friend);
    }

    @Transactional
    public void deleteFriend(Long userId, Long friendId) {
        Users user = usersService.findById(userId);
        Users friend = usersService.findById(friendId);
        user.getFriends().remove(friend);
    }

    @Transactional
    public void addBlock(Long userId, Long friendId) {
        Users user = usersService.findById(userId);
        Users friend = usersService.findById(friendId);
        user.getFriends().remove(friend);
        user.getBlocklist().add(friend);
    }
}
