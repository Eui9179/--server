package leui.woojoo.bounded_context.users.controller;

import leui.woojoo.bounded_context.users.dto.UserInList;
import leui.woojoo.bounded_context.users.dto.web.FriendIdRequest;
import leui.woojoo.bounded_context.users.service.RelationshipService;
import leui.woojoo.base.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api")
public class RelationshipController {

    private final RelationshipService relationshipService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/friends/me")
    public List<UserInList> getMyFriends(Principal principal) {
        Long userId = UserUtils.resolveUserId(principal);
        List<UserInList> myFriends = new ArrayList<>(relationshipService.findFriendInListAll(userId));
        Collections.sort(myFriends);
        return myFriends;
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/friends/sync")
    public String syncFriendList(Principal principal, @RequestBody List<String> phoneNumbers) {
        Long userId = UserUtils.resolveUserId(principal);
        relationshipService.syncFriends(userId, phoneNumbers);
        return "ok";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/friend")
    public UserInList insertFriend(Principal principal, @RequestBody FriendIdRequest friendRequest) {
        Long userId = UserUtils.resolveUserId(principal);
        Long friendId = friendRequest.getFriendId();
        return relationshipService.insertFriend(userId, friendId);
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/friend/{friendId}")
    public String deleteFriend(Principal principal, @PathVariable Long friendId) {
        Long userId = UserUtils.resolveUserId(principal);
        relationshipService.deleteFriend(userId, friendId);
        return "ok";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/block")
    public String blockUser(Principal principal, @RequestBody FriendIdRequest friendRequest) {
        Long userId = UserUtils.resolveUserId(principal);
        Long friendId = friendRequest.getFriendId();
        relationshipService.addBlock(userId, friendId);
        return "ok";
    }
}
