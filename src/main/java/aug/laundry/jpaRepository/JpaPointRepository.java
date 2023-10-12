package aug.laundry.jpaRepository;

import aug.laundry.domain.Point;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaPointRepository extends JpaRepository<Point, Long> {


}
