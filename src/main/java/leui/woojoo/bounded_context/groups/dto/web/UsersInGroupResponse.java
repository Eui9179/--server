package leui.woojoo.bounded_context.groups.dto.web;

import leui.woojoo.bounded_context.users.dto.UserInList;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsersInGroupResponse {
    List<UserInList> people;
    List<UserInList> friends;
}
