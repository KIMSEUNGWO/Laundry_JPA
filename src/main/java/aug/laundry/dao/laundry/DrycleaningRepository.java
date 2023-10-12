package aug.laundry.dao.laundry;

import aug.laundry.dao.jpaRepository.JpaDrycleaningRepository;
import aug.laundry.domain.Drycleaning;
import aug.laundry.dto.OrderDrycleaning;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static aug.laundry.domain.QDrycleaning.drycleaning;

@Slf4j
@Repository
public class DrycleaningRepository {

    private final LaundryMapper laundryMapper;
    private final JPAQueryFactory query;
    private final JpaDrycleaningRepository jpaDrycleaningRepository;

    public DrycleaningRepository(LaundryMapper laundryMapper, EntityManager em, JpaDrycleaningRepository jpaDrycleaningRepository) {
        this.laundryMapper = laundryMapper;
        this.query = new JPAQueryFactory(em);
        this.jpaDrycleaningRepository = jpaDrycleaningRepository;
    }

    public List<OrderDrycleaning> reloadDrycleaning(Long orderDetailId) {
        List<OrderDrycleaning> result = laundryMapper.reloadDrycleaning(orderDetailId);
        if (result == null || result.isEmpty()) return null;
        return result;
    }

    public void removeDrycleaning(Long ordersDetailId) {
        query.delete(drycleaning)
                .where(drycleaning.ordersDetailId.eq(ordersDetailId))
                .execute();
        log.info("REMOVE DRYCLEANING");
    }

    public void insertDryCleaning(Long ordersDetailId, String categoryName) {
        Drycleaning insertDry = new Drycleaning();
        insertDry.setOrdersDetailId(ordersDetailId);
        insertDry.setDrycleaningCategory(categoryName);
        jpaDrycleaningRepository.save(insertDry);
        log.info("INSERT DRYCLEANING = {}", insertDry);
    }
}
