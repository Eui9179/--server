package leui.woojoo.bounded_context.users.dto.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class FriendIdRequest {
    @JsonProperty("friend_id")
    private Long friendId;
}
