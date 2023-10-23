package leui.woojoo.bounded_context.users.dto.web;

import leui.woojoo.bounded_context.users.dto.UserInList;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewFriendResponse {
    private UserInList newFriend;
}
