package aug.laundry.jpaRepository;

import aug.laundry.domain.OrdersDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaOrdersDetailRepository extends JpaRepository<OrdersDetail, Long> {
}
