package aug.laundry.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QOrdersDetail is a Querydsl query type for OrdersDetail
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrdersDetail extends EntityPathBase<OrdersDetail> {

    private static final long serialVersionUID = 1770238100L;

    public static final QOrdersDetail ordersDetail = new QOrdersDetail("ordersDetail");

    public final NumberPath<Long> inspectionId = createNumber("inspectionId", Long.class);

    public final NumberPath<Long> memberId = createNumber("memberId", Long.class);

    public final NumberPath<Long> ordersDetailId = createNumber("ordersDetailId", Long.class);

    public final NumberPath<Long> ordersId = createNumber("ordersId", Long.class);

    public QOrdersDetail(String variable) {
        super(OrdersDetail.class, forVariable(variable));
    }

    public QOrdersDetail(Path<? extends OrdersDetail> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOrdersDetail(PathMetadata metadata) {
        super(OrdersDetail.class, metadata);
    }

}

