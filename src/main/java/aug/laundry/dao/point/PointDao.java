package aug.laundry.dao.point;

import aug.laundry.domain.Point;
import aug.laundry.domain.QPoint;
import aug.laundry.jpaRepository.JpaPointRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;



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
        return pointMapper.findByMemberId(memberId);
    }
    public int registerPoint(Long memberId) {
        Point point = new Point();
        point.setMemberId(memberId);
        point.setPointStack(1000);
        point.setPointStackReason("회원가입");
        point.setPointNow(1000);
        jpaPointRepository.save(point);
        return 1;
    }

    public int addPoint(Long memberId, Integer pointStack, String reason){
        Integer myCurrentPointPlusPoint =
                query.select(QPoint.point.pointNow)
                .from(QPoint.point)
                .where(QPoint.point.memberId.eq(memberId))
                .orderBy(QPoint.point.pointId.desc())
                .fetchFirst() + pointStack;
        Point p = new Point();
        p.setMemberId(memberId);
        p.setPointStack(pointStack);
        p.setPointStackReason(reason);
        p.setPointNow(myCurrentPointPlusPoint);
        jpaPointRepository.save(p);
        return 1;
    }

}
