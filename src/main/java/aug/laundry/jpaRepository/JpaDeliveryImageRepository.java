package aug.laundry.jpaRepository;

import aug.laundry.domain.DeliveryImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaDeliveryImageRepository extends JpaRepository<DeliveryImage, Long> {
}
