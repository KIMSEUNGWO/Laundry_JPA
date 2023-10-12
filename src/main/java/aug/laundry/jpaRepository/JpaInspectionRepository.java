package aug.laundry.jpaRepository;

import aug.laundry.domain.Inspection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaInspectionRepository extends JpaRepository<Inspection, Long> {
}
