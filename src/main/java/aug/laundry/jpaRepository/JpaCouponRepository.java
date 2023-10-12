package aug.laundry.jpaRepository;

import aug.laundry.domain.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCouponRepository extends JpaRepository<Coupon, Long>{
}
