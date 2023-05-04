package leui.woojoo.bounded_context.reports.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QReports is a Querydsl query type for Reports
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReports extends EntityPathBase<Reports> {

    private static final long serialVersionUID = 90984382L;

    public static final QReports reports = new QReports("reports");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> reportedUserId = createNumber("reportedUserId", Long.class);

    public final NumberPath<Integer> reportNumbers = createNumber("reportNumbers", Integer.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QReports(String variable) {
        super(Reports.class, forVariable(variable));
    }

    public QReports(Path<? extends Reports> path) {
        super(path.getType(), path.getMetadata());
    }

    public QReports(PathMetadata metadata) {
        super(Reports.class, metadata);
    }

}

