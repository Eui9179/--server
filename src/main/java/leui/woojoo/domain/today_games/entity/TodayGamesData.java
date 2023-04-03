package leui.woojoo.domain.today_games.entity;

import java.time.LocalDateTime;

public interface TodayGamesData {
    Long getTodaysGameId();
    Long getId();
    String getName();
    String getProfileImageName();
    String getGame();
    String getGameNickname();
    String getIntroduction();
    LocalDateTime getCreateTime();
}
