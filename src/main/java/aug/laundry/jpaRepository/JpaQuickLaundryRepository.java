package aug.laundry.jpaRepository;

import aug.laundry.domain.QuickLaundry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaQuickLaundryRepository extends JpaRepository<QuickLaundry, Long> {
}
