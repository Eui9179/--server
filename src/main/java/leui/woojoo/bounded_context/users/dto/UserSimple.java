package leui.woojoo.bounded_context.users.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UserSimple {
    private Long userId;
    private String name;
    private String profileImageName;
}
