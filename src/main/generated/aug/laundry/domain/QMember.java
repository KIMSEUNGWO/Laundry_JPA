package aug.laundry.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = -888641000L;

    public static final QMember member = new QMember("member1");

    public final StringPath account = createString("account");

    public final NumberPath<Long> gradeId = createNumber("gradeId", Long.class);

    public final StringPath memberAddress = createString("memberAddress");

    public final StringPath memberAddressDetails = createString("memberAddressDetails");

    public final StringPath memberCreateDate = createString("memberCreateDate");

    public final ComparablePath<Character> memberDeleteStatus = createComparable("memberDeleteStatus", Character.class);

    public final NumberPath<Long> memberId = createNumber("memberId", Long.class);

    public final StringPath memberInviteCode = createString("memberInviteCode");

    public final StringPath memberMyInviteCode = createString("memberMyInviteCode");

    public final StringPath memberName = createString("memberName");

    public final StringPath memberPhone = createString("memberPhone");

    public final StringPath memberRecentlyDate = createString("memberRecentlyDate");

    public final StringPath memberSocial = createString("memberSocial");

    public final NumberPath<Integer> memberZipcode = createNumber("memberZipcode", Integer.class);

    public final StringPath password = createString("password");

    public final StringPath sessionId = createString("sessionId");

    public final StringPath sessionLimit = createString("sessionLimit");

    public final StringPath subscriptionExpireDate = createString("subscriptionExpireDate");

    public final NumberPath<Long> subscriptionId = createNumber("subscriptionId", Long.class);

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

