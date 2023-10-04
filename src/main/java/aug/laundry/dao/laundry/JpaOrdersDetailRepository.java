package aug.laundry.dao.laundry;

import aug.laundry.domain.OrdersDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaOrdersDetailRepository extends JpaRepository<OrdersDetail, Long> {
}
