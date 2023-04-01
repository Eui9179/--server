package leui.woojoo.domain.users.dto.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import leui.woojoo.domain.users.dto.UserInList;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewFriendResponse {
    @JsonProperty("new_friend")
    private UserInList newFriend;
}
