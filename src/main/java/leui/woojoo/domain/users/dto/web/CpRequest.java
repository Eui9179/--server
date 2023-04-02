package leui.woojoo.domain.users.dto.web;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class CpRequest {
    private String cp;
    private String phoneNumber;
}
