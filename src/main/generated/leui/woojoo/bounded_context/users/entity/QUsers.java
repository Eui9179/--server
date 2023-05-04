package leui.woojoo.bounded_context.users.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUsers is a Querydsl query type for Users
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUsers extends EntityPathBase<Users> {

    private static final long serialVersionUID = -276955938L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUsers users = new QUsers("users");

    public final leui.woojoo.bounded_context.QBaseTimeEntity _super = new leui.woojoo.bounded_context.QBaseTimeEntity(this);

    public final SetPath<Users, QUsers> blocklist = this.<Users, QUsers>createSet("blocklist", Users.class, QUsers.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final StringPath fcmToken = createString("fcmToken");

    public final SetPath<Users, QUsers> friends = this.<Users, QUsers>createSet("friends", Users.class, QUsers.class, PathInits.DIRECT2);

    public final ListPath<leui.woojoo.bounded_context.games.entity.Games, leui.woojoo.bounded_context.games.entity.QGames> games = this.<leui.woojoo.bounded_context.games.entity.Games, leui.woojoo.bounded_context.games.entity.QGames>createList("games", leui.woojoo.bounded_context.games.entity.Games.class, leui.woojoo.bounded_context.games.entity.QGames.class, PathInits.DIRECT2);

    public final leui.woojoo.bounded_context.groups.entity.QGroups groups;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final StringPath phoneNumber = createString("phoneNumber");

    public final StringPath profileImageName = createString("profileImageName");

    public QUsers(String variable) {
        this(Users.class, forVariable(variable), INITS);
    }

    public QUsers(Path<? extends Users> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUsers(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUsers(PathMetadata metadata, PathInits inits) {
        this(Users.class, metadata, inits);
    }

    public QUsers(Class<? extends Users> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.groups = inits.isInitialized("groups") ? new leui.woojoo.bounded_context.groups.entity.QGroups(forProperty("groups"), inits.get("groups")) : null;
    }

}

