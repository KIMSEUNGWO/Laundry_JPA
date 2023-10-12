package aug.laundry.jpaRepository;

import aug.laundry.domain.QuickRider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaQuickRiderRepository extends JpaRepository<QuickRider, Long> {
}
