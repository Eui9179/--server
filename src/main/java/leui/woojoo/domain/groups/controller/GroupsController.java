package leui.woojoo.domain.groups.controller;

import leui.woojoo.domain.groups.dto.GroupSimple;
import leui.woojoo.domain.groups.service.GroupsService;
import leui.woojoo.domain.users.dto.UserInList;
import leui.woojoo.domain.users.service.UsersService;
import leui.woojoo.utils.UserUtils;
import leui.woojoo.domain.groups.dto.web.MyGroupResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/groups")
public class GroupsController {

    private final UsersService usersService;
    private final GroupsService groupsService;

    @GetMapping("/me")
    public MyGroupResponse getMyGroup(@AuthenticationPrincipal User user) {
        Long userId = UserUtils.resolveUserId(user);
        GroupSimple myGroup = usersService.findUserGroupsByUserId(userId);
        return MyGroupResponse.builder()
                .myGroups(List.of(myGroup))
                .build();
    }

    @GetMapping("/{name}")
    public Map<String, List<UserInList>> getPeopleByGroup(@AuthenticationPrincipal User user, @PathVariable("name") String groupName) {
        Long userId = UserUtils.resolveUserId(user);
        return groupsService.findUsersByGroup(userId, groupName, "");
    }

    @GetMapping("/{name}/{detail1}")
    public Map<String, List<UserInList>> getPeopleByGroupDetail(@AuthenticationPrincipal User user, @PathVariable("name") String groupName,
                                       @PathVariable("detail1") String groupDetail) {
        Long userId = UserUtils.resolveUserId(user);
        return groupsService.findUsersByGroup(userId, groupName, groupDetail);
    }
}
