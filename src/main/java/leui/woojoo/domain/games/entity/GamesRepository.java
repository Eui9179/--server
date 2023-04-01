package leui.woojoo.domain.games.entity;

import leui.woojoo.domain.games.entity.Games;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GamesRepository extends JpaRepository<Games, Long> {
    Games findGamesByGame(String game);
}
