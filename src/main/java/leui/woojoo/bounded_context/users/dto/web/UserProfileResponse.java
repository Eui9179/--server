package leui.woojoo.bounded_context.users.dto.web;

import leui.woojoo.bounded_context.games.dto.Game;
import leui.woojoo.bounded_context.groups.dto.GroupSimple;
import leui.woojoo.bounded_context.users.dto.UserInList;
import leui.woojoo.bounded_context.users.dto.UserSimple;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class UserProfileResponse {
    private UserSimple userSimple;
    private boolean isFriend;
    private GroupSimple group;
    private List<Game> games;
    private ArrayList<UserInList> alreadyFriends;
    private ArrayList<UserInList> userFriends;

    public boolean getIsFriend() {
        return this.isFriend;
    }
}
