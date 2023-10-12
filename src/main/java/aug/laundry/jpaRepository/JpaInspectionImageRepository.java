package aug.laundry.jpaRepository;

import aug.laundry.domain.InspectionImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaInspectionImageRepository extends JpaRepository<InspectionImage, Long> {
}
