package aug.laundry.jpaRepository;

import aug.laundry.domain.CouponList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCouponListRepository extends JpaRepository<CouponList, Long> {
}
