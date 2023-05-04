package leui.woojoo.bounded_context.users.dto.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import leui.woojoo.bounded_context.users.dto.UserInList;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class MyFriendsResponse {
    @JsonProperty("my_friends")
    private List<UserInList> myFriends;
}
