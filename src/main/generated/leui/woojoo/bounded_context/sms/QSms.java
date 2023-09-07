package leui.woojoo.bounded_context.sms;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import leui.woojoo.bounded_context.sms.entity.Sms;


/**
 * QSms is a Querydsl query type for Sms
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSms extends EntityPathBase<Sms> {

    private static final long serialVersionUID = -829780119L;

    public static final QSms sms = new QSms("sms");

    public final StringPath cp = createString("cp");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath phoneNumber = createString("phoneNumber");

    public QSms(String variable) {
        super(Sms.class, forVariable(variable));
    }

    public QSms(Path<? extends Sms> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSms(PathMetadata metadata) {
        super(Sms.class, metadata);
    }

}

