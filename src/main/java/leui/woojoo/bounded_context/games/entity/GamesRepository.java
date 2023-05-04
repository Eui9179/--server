package leui.woojoo.bounded_context.games.entity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GamesRepository extends JpaRepository<Games, Long> {
    Games findGamesByGame(String game);
}
