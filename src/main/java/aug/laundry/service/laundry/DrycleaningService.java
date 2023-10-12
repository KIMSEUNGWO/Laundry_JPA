package aug.laundry.service.laundry;

import aug.laundry.dto.OrderDrycleaning;

import java.util.List;
import java.util.Map;

public interface DrycleaningService {

    List<OrderDrycleaning> reloadDrycleaning(Long orderDetailId);

    boolean valid(Long memberId, Long ordersDetailId, Map<String, Integer> result);

    void removeDrycleaning(Long ordersDetailId);

    void insertDryclenaing(Long ordersDetailId, Map<String, Integer> result, Map<String, Boolean> resultMap);
}
