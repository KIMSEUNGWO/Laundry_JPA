package aug.laundry.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRider is a Querydsl query type for Rider
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRider extends EntityPathBase<Rider> {

    private static final long serialVersionUID = 391704380L;

    public static final QRider rider = new QRider("rider");

    public final StringPath account = createString("account");

    public final StringPath password = createString("password");

    public final StringPath riderAddress = createString("riderAddress");

    public final NumberPath<Long> riderId = createNumber("riderId", Long.class);

    public final StringPath riderName = createString("riderName");

    public final StringPath riderPhone = createString("riderPhone");

    public final StringPath riderPossibleZipcode = createString("riderPossibleZipcode");

    public final NumberPath<Integer> riderZipcode = createNumber("riderZipcode", Integer.class);

    public final StringPath workingArea = createString("workingArea");

    public QRider(String variable) {
        super(Rider.class, forVariable(variable));
    }

    public QRider(Path<? extends Rider> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRider(PathMetadata metadata) {
        super(Rider.class, metadata);
    }

}

