package aug.laundry.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QQuickRider is a Querydsl query type for QuickRider
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QQuickRider extends EntityPathBase<QuickRider> {

    private static final long serialVersionUID = -1852883701L;

    public static final QQuickRider quickRider = new QQuickRider("quickRider");

    public final StringPath account = createString("account");

    public final StringPath password = createString("password");

    public final StringPath quickRiderAddress = createString("quickRiderAddress");

    public final NumberPath<Long> quickRiderId = createNumber("quickRiderId", Long.class);

    public final StringPath quickRiderName = createString("quickRiderName");

    public final StringPath quickRiderPhone = createString("quickRiderPhone");

    public final NumberPath<Integer> quickRiderZipcode = createNumber("quickRiderZipcode", Integer.class);

    public QQuickRider(String variable) {
        super(QuickRider.class, forVariable(variable));
    }

    public QQuickRider(Path<? extends QuickRider> path) {
        super(path.getType(), path.getMetadata());
    }

    public QQuickRider(PathMetadata metadata) {
        super(QuickRider.class, metadata);
    }

}

