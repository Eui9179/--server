package leui.woojoo.domain.games.repository;

import leui.woojoo.domain.games.Games;
import leui.woojoo.domain.users.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GamesRepository extends JpaRepository<Games, Long> {
    Games findGamesByGame(String game);
}
