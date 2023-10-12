package aug.laundry.jpaRepository;

import aug.laundry.domain.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaGradeRepository extends JpaRepository<Grade, Long> {
}
