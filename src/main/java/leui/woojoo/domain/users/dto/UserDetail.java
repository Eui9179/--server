package leui.woojoo.domain.users.dto;

import leui.woojoo.domain.users.entity.Users;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDetail {
    private Long id;
    private String name;
    private String phoneNumber;
    private String profileImageName;
    private String fcmToken;

    public UserDetail(Users users) {
        this.id = users.getId();
        this.name = users.getName();
        this.phoneNumber = users.getPhoneNumber();
        this.profileImageName = users.getProfileImageName();
        this.fcmToken = users.getFcmToken();
    }
}
