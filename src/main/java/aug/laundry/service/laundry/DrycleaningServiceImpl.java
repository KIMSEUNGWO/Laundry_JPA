package aug.laundry.service.laundry;

import aug.laundry.dao.laundry.DrycleaningRepository;
import aug.laundry.dto.OrderDrycleaning;
import aug.laundry.enums.category.Category;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class DrycleaningServiceImpl implements DrycleaningService{

    private final DrycleaningRepository drycleaningRepository;

    @Override
    public List<OrderDrycleaning> reloadDrycleaning(Long orderDetailId) {

        return drycleaningRepository.reloadDrycleaning(orderDetailId);
    }

    @Override
    public boolean valid(Long memberId, Long ordersDetailId, Map<String, Integer> result) {
        if (memberId == null || ordersDetailId == null) return false;

        for (String category : result.keySet()) {
            if (Category.findByTitle(category).isEmpty()) return false; // 해당하는 카테고리가 없으면 false 반환
        }

        return true;
    }

    @Override
    public void removeDrycleaning(Long ordersDetailId) {
        drycleaningRepository.removeDrycleaning(ordersDetailId);
    }

    @Override
    public void insertDryclenaing(Long ordersDetailId, Map<String, Integer> result, Map<String, Boolean> resultMap) {
        if (result.isEmpty()) {
            resultMap.put("empty", true);
            return; // 기존에 존재하던 장바구니에 있는 목록들을 모두 삭제했을경우 추가하지않고 return
        }

        for (String category : result.keySet()) {
            Category category1 = Category.findByTitle(category).get();
            int amount = result.get(category1.getTitle());
            for (int i=0;i<amount;i++){
                drycleaningRepository.insertDryCleaning(ordersDetailId, category1.name());
                log.info("Category = {}, amount = {}", category1, amount);
            }
        }
    }
}
