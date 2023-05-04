package leui.woojoo.bounded_context.today_games.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTodayGames is a Querydsl query type for TodayGames
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTodayGames extends EntityPathBase<TodayGames> {

    private static final long serialVersionUID = 1554996389L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTodayGames todayGames = new QTodayGames("todayGames");

    public final leui.woojoo.bounded_context.QBaseTimeEntity _super = new leui.woojoo.bounded_context.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final StringPath descriptions = createString("descriptions");

    public final StringPath gameName = createString("gameName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final leui.woojoo.bounded_context.users.entity.QUsers users;

    public QTodayGames(String variable) {
        this(TodayGames.class, forVariable(variable), INITS);
    }

    public QTodayGames(Path<? extends TodayGames> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTodayGames(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTodayGames(PathMetadata metadata, PathInits inits) {
        this(TodayGames.class, metadata, inits);
    }

    public QTodayGames(Class<? extends TodayGames> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.users = inits.isInitialized("users") ? new leui.woojoo.bounded_context.users.entity.QUsers(forProperty("users"), inits.get("users")) : null;
    }

}

