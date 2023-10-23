package leui.woojoo.bounded_context.users.dto.web;

import leui.woojoo.bounded_context.groups.entity.Groups;
import leui.woojoo.bounded_context.users.entity.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Slf4j
@NoArgsConstructor
public class SignupRequest {
    String name;
    String phoneNumber;
    MultipartFile file;
    String groups;
    String detail1;
    String fcmToken;

    @Builder
    public SignupRequest(String name, String phoneNumber, MultipartFile file, String groups, String detail1, String fcmToken) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.file = file;
        this.groups = groups;
        this.detail1 = detail1;
        this.fcmToken = fcmToken;
    }

    public Users toUserEntity(String profileImageName) {
        return Users.builder()
                .name(name)
                .phoneNumber(phoneNumber)
                .profileImageName(profileImageName)
                .fcmToken(fcmToken)
                .build();
    }

    public Groups toUserGroupEntity(Users users) {
        return Groups.builder()
                .users(users)
                .groupName(groups)
                .detail1(detail1)
                .build();
    }
}
