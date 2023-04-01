package leui.woojoo.domain.today_games.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import leui.woojoo.domain.games.entity.Games;
import leui.woojoo.domain.today_games.entity.TodayGames;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TodayGameDetail {
    private Long todaysGameId;
    private Long id;
    private String name;
    private String profileImageName;
    private String game;
    private String gameNickname;
    private String introduction;
    private String createTime;
    private boolean isme;

    public static TodayGameDetail of(TodayGames entity, Long userId) {
        return new TodayGameDetail(entity, userId);
    }
    public TodayGameDetail(TodayGames entity, Long userId) {
        this.todaysGameId = entity.getId();
        this.id = entity.getUsers().getId();
        this.name = entity.getUsers().getName();
        this.profileImageName = entity.getUsers().getProfileImageName();
        this.game = entity.getGameName();
        this.introduction = entity.getDescriptions();
        this.createTime = entity.getCreatedDate().toString();
        this.isme = userId.equals(entity.getUsers().getId());
        this.gameNickname = entity.getUsers().getGames().get(0).getNickname();

        for (Games games : entity.getUsers().getGames()) {
            if (games.getGame().equals(this.game)) {
                this.gameNickname = games.getNickname();
                break;
            }
        }
    }
}
