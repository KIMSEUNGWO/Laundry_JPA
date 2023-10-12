package aug.laundry.service.laundry;

import aug.laundry.domain.Repair;
import aug.laundry.domain.RepairImage;
import aug.laundry.dto.RepairFormData;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface RepairService {
    List<Repair> reloadRepair(Long orderDetailId);

    Map<Long, List<RepairImage>> getRepairImage(List<Repair> reload);

    void removeRepair(List<Repair> repairList);

    boolean valid(Long memberId, Long ordersDetailId);

    Repair insertRepair(Long ordersDetailId, Map<String, RepairFormData> repairData, List<MultipartFile> files);
}
