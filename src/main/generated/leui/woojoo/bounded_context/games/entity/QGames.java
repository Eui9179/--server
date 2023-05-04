package leui.woojoo.bounded_context.games.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGames is a Querydsl query type for Games
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGames extends EntityPathBase<Games> {

    private static final long serialVersionUID = -13684354L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QGames games = new QGames("games");

    public final StringPath game = createString("game");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath nickname = createString("nickname");

    public final leui.woojoo.bounded_context.users.entity.QUsers users;

    public QGames(String variable) {
        this(Games.class, forVariable(variable), INITS);
    }

    public QGames(Path<? extends Games> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QGames(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QGames(PathMetadata metadata, PathInits inits) {
        this(Games.class, metadata, inits);
    }

    public QGames(Class<? extends Games> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.users = inits.isInitialized("users") ? new leui.woojoo.bounded_context.users.entity.QUsers(forProperty("users"), inits.get("users")) : null;
    }

}

