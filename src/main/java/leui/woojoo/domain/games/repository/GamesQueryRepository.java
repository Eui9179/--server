package leui.woojoo.domain.games.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import leui.woojoo.domain.games.Games;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static leui.woojoo.domain.games.QGames.*;
import static leui.woojoo.domain.users.QUsers.users;

@RequiredArgsConstructor
@Repository
public class GamesQueryRepository {
    private final JPAQueryFactory queryFactory;

    public Optional<Games> findByUserIdAndGame(Long userId, String game) {
        return Optional.ofNullable(
                queryFactory
                        .selectFrom(games)
                        .where(users.id.eq(userId)
                                .and(games.game.eq(game)))
                        .fetchOne()
        );
    }
}
