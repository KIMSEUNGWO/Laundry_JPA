package aug.laundry.service.laundry;

import aug.laundry.dao.laundry.RepairRepository;
import aug.laundry.domain.Repair;
import aug.laundry.domain.RepairImage;
import aug.laundry.dto.InsertRepairDto;
import aug.laundry.dto.RepairFormData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class RepairServiceImpl implements RepairService{

    private final RepairRepository repairRepository;

    @Override
    public List<Repair> reloadRepair(Long orderDetailId) {
        return repairRepository.reloadRepair(orderDetailId);
    }

    @Override
    public Map<Long, List<RepairImage>> getRepairImage(List<Repair> reload) {
        Map<Long, List<RepairImage>> result = new ConcurrentHashMap<>();
        for (Repair orderRepair : reload) {
            List<RepairImage> repairImage = repairRepository.getRepairImage(orderRepair.getRepairId());
            result.put(orderRepair.getRepairId(), repairImage);
        }
        return result;
    }

    @Transactional
    @Override
    public void removeRepair(List<Repair> repairList) {
        if (repairList.isEmpty()) return;

        for (Repair repair : repairList) {
            Long repairId = repair.getRepairId();
            List<RepairImage> repairImage = repairRepository.getRepairImage(repairId);
            repairRepository.removeRepairImageFile(repairImage);
            repairRepository.removeRepairImages(repairId);
        }
    }

    @Override
    public boolean valid(Long memberId, Long ordersDetailId) {
        return memberId != null && ordersDetailId != null;
    }

    @Override
    public Repair insertRepair(Long ordersDetailId, Map<String, RepairFormData> repairData, List<MultipartFile> files) {
        Repair insertRepair = null;
        for (String s : repairData.keySet()) { // s = 1 ~ 시작되는 숫자
            insertRepair = repairRepository.insertRepair(ordersDetailId, repairData.get(s).getRequest(), repairData.get(s).getTitle());
            log.info("insertRepair = {}", insertRepair);
            break;
        }
        return insertRepair;
    }

}
