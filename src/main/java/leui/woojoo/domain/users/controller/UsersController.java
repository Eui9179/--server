package leui.woojoo.domain.users.controller;

import leui.woojoo.domain.users.dto.UserDetail;
import leui.woojoo.domain.users.dto.UserProfileUpdate;
import leui.woojoo.domain.users.dto.web.UserProfileResponse;
import leui.woojoo.domain.users.dto.web.UserSettingRequest;
import leui.woojoo.domain.users.service.UsersService;
import leui.woojoo.utils.FileUtils;
import leui.woojoo.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UsersController {

    private final UsersService usersService;
    private final FileUtils fileUtils;

    @GetMapping("/me")
    public UserDetail getMyProfile(@AuthenticationPrincipal User user) {
        log.info("getMyProfile id = {}", UserUtils.resolveUserId(user));
        return usersService.findUserDetailById(UserUtils.resolveUserId(user));
    }

    @GetMapping("/{userId}")
    public UserProfileResponse getUserProfile(
            @AuthenticationPrincipal User user, @PathVariable Long userId) {
        Long myId = UserUtils.resolveUserId(user);
        return usersService.findUserProfileByUserIdAndOtherId(myId, userId);
    }

    @PostMapping("/setting")
    public ResponseEntity<String> setProfile(@AuthenticationPrincipal User user, @ModelAttribute UserSettingRequest request) throws IOException {
        Long userId = UserUtils.resolveUserId(user);
        UserDetail userDetail = usersService.findUserDetailById(userId);

        UserProfileUpdate userProfileUpdate = new UserProfileUpdate();
        String profileImageName = "default.png";

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

        if (!usersService.updateUserProfile(userId, userProfileUpdate)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(profileImageName, HttpStatus.OK);
    }

}
