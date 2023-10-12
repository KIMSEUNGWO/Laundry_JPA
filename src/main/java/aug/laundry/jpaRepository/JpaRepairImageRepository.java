package aug.laundry.jpaRepository;

import aug.laundry.domain.RepairImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JpaRepairImageRepository extends JpaRepository<RepairImage, Long> {

    List<RepairImage> findAllByRepairId(Long repairId);
}
