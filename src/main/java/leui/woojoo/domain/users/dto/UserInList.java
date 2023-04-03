package leui.woojoo.domain.users.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import leui.woojoo.domain.games.entity.Games;
import leui.woojoo.domain.users.entity.Users;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserInList implements Comparable<UserInList> {
    private Long id;
    private String name;
    private String profileImageName;
    private List<String> games;

    public UserInList(Users user) {
        this.id = user.getId();
        this.name = user.getName();
        this.profileImageName = user.getProfileImageName();
        this.games = user.getGames()
                .stream()
                .map(Games::getGame)
                .toList();
    }

    @Override
    public int compareTo(UserInList f) {
        return this.name.compareTo(f.name);
    }
}
