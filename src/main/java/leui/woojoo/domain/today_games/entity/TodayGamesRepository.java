package leui.woojoo.domain.today_games.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface TodayGamesRepository extends JpaRepository<TodayGames, Long> {
    List<TodayGames> findAllByCreatedDateBetweenOrderByIdDesc(LocalDateTime today, LocalDateTime tomorrow);

    @Query(value = "SELECT DISTINCT(TG.id) AS todaysGameId, U.id as id, U.name as name, " +
            "U.profile_image_name as profileImageName, TG.game_name as game, G.nickname as gameNickname, " +
            "TG.descriptions as introduction, TG.created_date as createTime " +
            "FROM today_games AS TG " +
            "INNER JOIN users AS U " +
            "INNER JOIN games AS G " +
            "ON TG.users_id = U.id " +
            "AND U.id = G.users_id " +
            "AND G.game = TG.game_name " +
            "WHERE TG.created_date BETWEEN :today AND :tomorrow " +
            "AND TG.users_id IN " +
            "(SELECT friends_id FROM users_friends WHERE users_id=:userId OR friends_id=:userId) " +
            "ORDER BY TG.created_date DESC;",
            nativeQuery = true)
    List<TodayGamesData> findTodayGames(
            @Param("userId") Long userId,
            @Param("today") LocalDateTime today,
            @Param("tomorrow") LocalDateTime tomorrow);
}
