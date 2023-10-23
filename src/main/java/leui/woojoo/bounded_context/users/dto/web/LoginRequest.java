package leui.woojoo.bounded_context.users.dto.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequest {
    private String phoneNumber;
    private String fcmToken;
    private String smsCode;
}
