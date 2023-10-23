package leui.woojoo.bounded_context.users.dto.web;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class PhoneNumberRequest {
    private String phoneNumber;
}
