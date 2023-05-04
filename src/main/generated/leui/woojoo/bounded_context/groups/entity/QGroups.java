package leui.woojoo.bounded_context.groups.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGroups is a Querydsl query type for Groups
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGroups extends EntityPathBase<Groups> {

    private static final long serialVersionUID = -1040407648L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QGroups groups = new QGroups("groups");

    public final StringPath detail1 = createString("detail1");

    public final StringPath groupName = createString("groupName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final leui.woojoo.bounded_context.users.entity.QUsers users;

    public QGroups(String variable) {
        this(Groups.class, forVariable(variable), INITS);
    }

    public QGroups(Path<? extends Groups> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QGroups(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QGroups(PathMetadata metadata, PathInits inits) {
        this(Groups.class, metadata, inits);
    }

    public QGroups(Class<? extends Groups> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.users = inits.isInitialized("users") ? new leui.woojoo.bounded_context.users.entity.QUsers(forProperty("users"), inits.get("users")) : null;
    }

}

