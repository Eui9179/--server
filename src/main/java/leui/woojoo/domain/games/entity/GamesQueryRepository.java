package leui.woojoo.domain.games.entity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static leui.woojoo.domain.games.entity.QGames.games;
import static leui.woojoo.domain.users.entity.QUsers.users;

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
