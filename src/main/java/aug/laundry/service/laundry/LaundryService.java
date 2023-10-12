package aug.laundry.service.laundry;

import aug.laundry.domain.Member;
import aug.laundry.domain.OrdersDetail;
import aug.laundry.dto.*;
import aug.laundry.enums.category.Category;
import aug.laundry.enums.category.MemberShip;
import aug.laundry.enums.repair.RepairCategory;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface LaundryService {


    List<MyCoupon> getCoupon(Long memberId);

    Address getAddress(Long memberId);

    List<Category> getDry(Long memberId, Long ordersDetailId);

    List<RepairCategory> getRepair(Long memberId, Long ordersDetailId);


    Long update(Long memberId, Long couponListId, OrderPost orderPost, Long ordersDetailId);

    void check(Long memberId, HttpSession session);

    Map<Long, List<String>> getRepairImage(List<OrderRepair> reload);

    boolean insertRepair(Long memberId, Long ordersDetailId, HashMap<String, Boolean> resultMap, Map<String, RepairFormData> repairData, List<MultipartFile> files);

    void insertCommon(Long ordersDetailId);

    void insertQuickLaundry(Long ordersDetailId);

    OrderInfo orderInfo(Model model);


    void removeRepair(Long ordersDetailId);


    Optional<Member> checkAddress(Long memberId);

    OrdersDetail createOrdersDetail(Long memberId);

    void checkOrder(Long ordersDetailId, String option, List<String> service);
}
