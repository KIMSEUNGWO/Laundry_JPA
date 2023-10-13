package aug.laundry.dao.point;

import aug.laundry.domain.Point;
import aug.laundry.domain.QMember;
import aug.laundry.domain.QPoint;
import aug.laundry.jpaRepository.JpaPointRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static aug.laundry.domain.QPoint.*;

@Repository
@Slf4j
@Transactional
public class PointDao {

    private final PointMapper pointMapper;
    private final JpaPointRepository jpaPointRepository;
    private final JPAQueryFactory query;

    public PointDao(PointMapper pointMapper, JpaPointRepository jpaPointRepository, EntityManager em) {
        this.pointMapper = pointMapper;
        this.jpaPointRepository = jpaPointRepository;
        this.query = new JPAQueryFactory(em);
    }

    public Integer findByMemberId(Long memberId) {
        return query.select(point.pointNow)
                .from(point)
                .where(point.memberId.eq(memberId))
                .orderBy(point.pointId.desc())
                .fetchFirst();
    }

    public int addPoint(Long memberId, Integer pointStack, String reason){
        // 가장 최근 point + pointStack = pointNow
        Integer myPointNow = query.select(point.pointNow)
                .from(point)
                .where(point.memberId.eq(memberId))
                .orderBy(point.pointId.desc())
                .fetchFirst();
        Integer myCurrentPointPlusPoint = ( myPointNow == null ) ? pointStack : myPointNow + pointStack ;

        Point p = new Point();
        p.setMemberId(memberId);
        p.setPointStack(pointStack);
        p.setPointStackReason(reason);
        p.setPointNow(myCurrentPointPlusPoint);
        jpaPointRepository.save(p);
        return 1;
    }

}
