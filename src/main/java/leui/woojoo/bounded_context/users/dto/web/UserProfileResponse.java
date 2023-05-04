package leui.woojoo.bounded_context.users.dto.web;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import leui.woojoo.bounded_context.games.dto.Game;
import leui.woojoo.bounded_context.groups.dto.GroupSimple;
import leui.woojoo.bounded_context.users.dto.UserInList;
import leui.woojoo.bounded_context.users.dto.UserSimple;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserProfileResponse {
    private UserSimple userProfile;
    private boolean isFriend;
    private List<GroupSimple> userGroups;
    private List<Game> userGames;
    private ArrayList<UserInList> alreadyFriends;
    private ArrayList<UserInList> userFriends;

    public UserSimple getUserProfile() {
        return this.userProfile;
    }

    public boolean getIsFriend() {
        return this.isFriend;
    }

    public List<GroupSimple> getUserGroups() {
        return this.userGroups;
    }

    public List<Game> getUserGames() {
        return this.userGames;
    }

    public ArrayList<UserInList> getAlreadyFriends() {
        return this.alreadyFriends;
    }

    public ArrayList<UserInList> getUserFriends() {
        return this.userFriends;
    }
}
