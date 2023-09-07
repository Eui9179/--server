package leui.woojoo.bounded_context.games.repository;

import leui.woojoo.bounded_context.games.entity.Games;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GamesRepository extends JpaRepository<Games, Long> {
    Games findGamesByGame(String game);
}
