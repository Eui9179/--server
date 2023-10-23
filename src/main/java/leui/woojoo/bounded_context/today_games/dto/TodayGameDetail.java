package leui.woojoo.bounded_context.today_games.dto;

import leui.woojoo.bounded_context.today_games.repository.TodayGamesData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class TodayGameDetail {
    private Long id;
    private Long userId;
    private String userName;
    private String profileImageName;
    private String gameName;
    private String gameNickname;
    private String description;
    private String createdTime;
    private boolean isMe;

    public static TodayGameDetail of(TodayGamesData entity, Long userId) {
        return new TodayGameDetail(entity, userId);
    }

    public TodayGameDetail(TodayGamesData entity, Long userId) {
        this.id = entity.getTodaysGameId();
        this.userId = userId;
        this.userName = entity.getName();
        this.profileImageName = entity.getProfileImageName();
        this.gameName = entity.getGame();
        this.description = entity.getIntroduction();
        this.createdTime = entity.getCreateTime().toString();
        this.isMe = userId.equals(entity.getId());
        this.gameNickname = entity.getGameNickname();
    }

    public boolean getIsMe() {
        return this.isMe;
    }
}
