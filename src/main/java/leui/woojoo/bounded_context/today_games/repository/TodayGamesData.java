package leui.woojoo.bounded_context.today_games.repository;

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
