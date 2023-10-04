package aug.laundry.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSocialUniqueNum is a Querydsl query type for SocialUniqueNum
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSocialUniqueNum extends EntityPathBase<SocialUniqueNum> {

    private static final long serialVersionUID = -1974629558L;

    public static final QSocialUniqueNum socialUniqueNum = new QSocialUniqueNum("socialUniqueNum");

    public final NumberPath<Long> memberId = createNumber("memberId", Long.class);

    public final StringPath socialUid = createString("socialUid");

    public QSocialUniqueNum(String variable) {
        super(SocialUniqueNum.class, forVariable(variable));
    }

    public QSocialUniqueNum(Path<? extends SocialUniqueNum> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSocialUniqueNum(PathMetadata metadata) {
        super(SocialUniqueNum.class, metadata);
    }

}

