package leui.woojoo.domain.users.dto.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginRequest {
    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("fcm_token")
    private String fcmToken;

    @JsonProperty("sms_code")
    private String smsCode;
}
