package leui.woojoo.bounded_context.users.dto.web;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CpRequest {
    private String cp;
    private String phoneNumber;
}
