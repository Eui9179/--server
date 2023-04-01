package leui.woojoo.domain.today_games.entity;

import leui.woojoo.domain.today_games.entity.TodayGames;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TodayGamesRepository extends JpaRepository<TodayGames, Long> {
    List<TodayGames> findAllByCreatedDateBetweenOrderByIdDesc(LocalDateTime today, LocalDateTime tomorrow);
}
