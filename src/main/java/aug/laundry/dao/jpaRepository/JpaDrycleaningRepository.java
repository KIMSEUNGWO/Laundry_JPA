package aug.laundry.dao.jpaRepository;

import aug.laundry.domain.Drycleaning;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaDrycleaningRepository extends JpaRepository<Drycleaning, Long> {
}
