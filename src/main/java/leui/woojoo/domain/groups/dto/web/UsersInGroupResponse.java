package leui.woojoo.domain.groups.dto.web;

import leui.woojoo.domain.users.dto.UserInList;
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
