package aug.laundry.dao.jpaRepository;

import aug.laundry.domain.Repair;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaRepairRepository extends JpaRepository<Repair, Long> {

    List<Repair> findAllByOrdersDetailId(Long ordersDetailId);
}
