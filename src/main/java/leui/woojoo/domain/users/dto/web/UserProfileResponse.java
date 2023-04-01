package leui.woojoo.domain.users.dto.web;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import leui.woojoo.domain.users.dto.UserSimple;
import leui.woojoo.domain.users.dto.UserInList;
import leui.woojoo.domain.games.dto.Game;
import leui.woojoo.domain.groups.dto.GroupSimple;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
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
}
