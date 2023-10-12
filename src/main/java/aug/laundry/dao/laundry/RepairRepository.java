package aug.laundry.dao.laundry;

import aug.laundry.jpaRepository.JpaRepairImageRepository;
import aug.laundry.jpaRepository.JpaRepairRepository;
import aug.laundry.domain.Repair;
import aug.laundry.domain.RepairImage;
import aug.laundry.enums.repair.RepairCategory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.io.File;
import java.net.URLDecoder;
import java.util.List;

import static aug.laundry.domain.QRepairImage.repairImage;

@Slf4j
@Repository
@Transactional
public class RepairRepository {

    private final JpaRepairRepository jpaRepairRepository;
    private final JpaRepairImageRepository jpaRepairImageRepository;
    private final JPAQueryFactory query;

    @Value("${repair.images}")
    private String directory;

    public RepairRepository(JpaRepairRepository jpaRepairRepository, JpaRepairImageRepository jpaRepairImageRepository, EntityManager em) {
        this.jpaRepairRepository = jpaRepairRepository;
        this.jpaRepairImageRepository = jpaRepairImageRepository;
        this.query = new JPAQueryFactory(em);
    }

    public List<Repair> reloadRepair(Long orderDetailId) {
        return jpaRepairRepository.findAllByOrdersDetailId(orderDetailId);
    }

    public List<RepairImage> getRepairImage(Long repairId) {
        return jpaRepairImageRepository.findAllByRepairId(repairId);
    }

    public void removeRepairImages(Long repairId) {
        query.delete(repairImage).where(repairImage.repairId.eq(repairId)).execute();
        log.info("Removed RepairImage");
    }



    public void removeRepairImageFile(List<RepairImage> repairImageStoreNames) { // 실제 RepairImage 파일 제거
        // 실제저장된 Repair Image 파일 삭제
        for (RepairImage repairImage : repairImageStoreNames) {
            String repairImageStoreName = repairImage.getRepairImageStoreName();
            if (repairImageStoreName != null) {
                String srcFileName = URLDecoder.decode(directory + repairImageStoreName);
                File file = new File(srcFileName);
                boolean delete = file.delete();
                log.info("Removed srcFileName = {} 삭제여부 = {}", srcFileName, delete);
            }
        }
    }


    public Repair insertRepair(Long ordersDetailId, String request, String title) {
        Repair repair = new Repair();
        repair.setOrdersDetailId(ordersDetailId);
        repair.setRepairRequest(request);
        repair.setRepairCategory(RepairCategory.valueOf(title));
        Repair save = jpaRepairRepository.save(repair);
        return save;
    }
}
