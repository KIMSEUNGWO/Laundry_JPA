package aug.laundry.jpaRepository;

import aug.laundry.domain.CommonLaundry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCommonLaundryRepository extends JpaRepository<CommonLaundry, Long> {
}
