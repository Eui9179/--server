package leui.woojoo.domain.users.dto.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class FriendIdRequest {
    @JsonProperty("friend_id")
    private Long friendId;
}
