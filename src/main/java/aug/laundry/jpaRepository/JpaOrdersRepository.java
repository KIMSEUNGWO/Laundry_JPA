package aug.laundry.jpaRepository;

import aug.laundry.domain.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaOrdersRepository extends JpaRepository<Orders, Long> {
}
