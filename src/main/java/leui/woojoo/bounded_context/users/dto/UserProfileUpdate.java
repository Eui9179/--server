package leui.woojoo.bounded_context.users.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileUpdate {
    private String profileImageName;
    private String name;
    private String groupName;
    private String groupDetail;
}
