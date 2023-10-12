package aug.laundry.jpaRepository;

import aug.laundry.domain.Rider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaRiderRepository extends JpaRepository<Rider, Long> {
}
