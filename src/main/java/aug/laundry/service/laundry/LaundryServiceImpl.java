package aug.laundry.service.laundry;

import aug.laundry.dao.laundry.LaundryRepository;
import aug.laundry.dao.member.MemberRepository;
import aug.laundry.domain.*;
import aug.laundry.dto.*;
import aug.laundry.enums.category.Category;
import aug.laundry.enums.category.Delivery;
import aug.laundry.enums.category.MemberShip;
import aug.laundry.enums.fileUpload.FileUploadType;
import aug.laundry.enums.repair.RepairCategory;
import aug.laundry.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class LaundryServiceImpl implements LaundryService{

    private final LaundryRepository laundryRepository;
    private final FileUploadService fileUploadService;
    private final MemberRepository memberRepository;

    @Override
    public List<MyCoupon> getCoupon(Long memberId) {
        return laundryRepository.getCoupon(memberId);
    }

    @Override
    public Address getAddress(Long memberId) {
        return laundryRepository.getAddress(memberId);
    }

    @Override
    public List<Category> getDry(Long memberId, Long ordersDetailId) {
        return laundryRepository.getDry(memberId, ordersDetailId);
    }

    @Override
    public List<RepairCategory> getRepair(Long memberId, Long ordersDetailId) {
        return laundryRepository.getRepair(memberId, ordersDetailId);
    }

    @Transactional
    @Override
    public Long update(Long memberId, Long couponListId, OrderPost orderPost, Long ordersDetailId) {

        boolean validCoupon = laundryRepository.validCoupon(memberId, couponListId); // 쿠폰 유효성 검사
        Long expectedPrice = 0L;
        if (validCoupon) {
            expectedPrice -= laundryRepository.getCouponDiscount(memberId, couponListId);
        }
        System.out.println("orderPost = " + orderPost);
        Orders orders = getOrders(memberId, orderPost);

        MemberShip memberShip = new MemberShip(memberRepository.isPass(memberId));
        OrderInfo orderInfo = laundryRepository.firstInfo(memberId, ordersDetailId); // 빠른세탁, 드라이클리닝, 생활빨래, 수선
        System.out.println("orderInfo = " + orderInfo);
        expectedPrice = getExpectedPrice(memberId, ordersDetailId, expectedPrice, orderInfo, memberShip);
        orders.setOrdersStatus(2);
        System.out.println("최종 orders = " + orders);
        System.out.println("ordersId = " + orders.getOrdersId());

        orders.setOrdersExpectedPrice(Math.round(expectedPrice / 100) * 100); // 예상금액 계산
        laundryRepository.insert(orders); // orders에 값을 넣고 ordersId 가져오기
        if (validCoupon) {
            laundryRepository.useCoupon(memberId, couponListId, orders.getOrdersId()); // 쿠폰 업데이트 (status +1, ordersId 주입)
        }
        laundryRepository.updateOrdersDetail(orders.getOrdersId(), ordersDetailId); // ORDERS_DETAIL 안에 ORDERS_ID 업데이트
        laundryRepository.insertInspection(orders.getOrdersId());

        System.out.println("성공!");
        return orders.getOrdersId();
    }

    @NotNull
    private Long getExpectedPrice(Long memberId, Long ordersDetailId, Long expectedPrice, OrderInfo orderInfo, MemberShip memberShip) {
        expectedPrice += orderInfo.isQuick() ? Delivery.QUICK_DELIVERY.getPrice() : Delivery.COMMON_DELIVERY.getPrice();
        if (orderInfo.isCommon()) expectedPrice += memberShip.apply(Category.BASIC.getPrice());
        if (orderInfo.isDry()) expectedPrice += memberShip.apply(laundryRepository.getDry(memberId, ordersDetailId).stream().map(x -> x.getPrice()).reduce((a, b) -> a + b).get());
        if (orderInfo.isRepair()) expectedPrice += memberShip.apply(laundryRepository.getRepair(memberId, ordersDetailId).stream().map(x -> x.getPrice()).reduce((a, b) -> a + b).get());
        return expectedPrice;
    }

    @Transactional
    @Override
    public void check(Long memberId, HttpSession session) {
        Long ordersDetailId =  laundryRepository.check(memberId, null);
        if (ordersDetailId != null && ordersDetailId != 0){ // 장바구니가 존재한다면
            laundryRepository.removeDryCleaning(ordersDetailId); // 드라이클리닝 장바구니 삭제
            laundryRepository.removeCommon(ordersDetailId); // 생활빨래 장바구니 삭제
            removeRepair(ordersDetailId); // 수선 데이터 전체삭제
            laundryRepository.removeQuickLaundry(ordersDetailId); // 빠른세탁 옵션 삭제
            laundryRepository.removeOrdersDetail(ordersDetailId); // 장바구니 전체 삭제

        }
    }

    private void removeRepairImages(List<Repair> repairIdAll) {
        for (Repair repair : repairIdAll) {
            Long repairId = repair.getRepairId();
            List<RepairImage> repairImageStoreNames = laundryRepository.getRepairImage(repairId);
            log.info("repairImageStoreNames = {}", repairImageStoreNames);
            laundryRepository.removeRepairImageFile(repairImageStoreNames); // 실제 수선이미지파일 제거
            laundryRepository.removeRepairImages(repairId); // DB에서 삭제
        }
    }

    @Override
    public Map<Long, List<String>> getRepairImage(List<OrderRepair> reload) {

        Map<Long, List<String>> result = new ConcurrentHashMap<>();
        for (OrderRepair orderRepair : reload) {
            List<RepairImage> repairImage = laundryRepository.getRepairImage(orderRepair.getRepairId());
            List<String> storeImageName = repairImage.stream().map(x -> x.getRepairImageStoreName()).collect(Collectors.toList());
            result.put(orderRepair.getRepairId(), storeImageName);
        }
        return result;
    }

    @Transactional
    @Override
    public boolean insertRepair(Long memberId, Long ordersDetailId, HashMap<String, Boolean> resultMap, Map<String, RepairFormData> repairData, List<MultipartFile> files) {
        Long check = laundryRepository.check(memberId, ordersDetailId);
        if (check == null || check == 0L) return false;

        // 사진을 하나하나 넣는거라서 기존 장바구니 삭제 못함
//        laundryRepository.removeRepair(ordersDetailId); // 기존에 존재하던 수선 장바구니 삭제

        if (repairData.isEmpty()){ // 기존에 있던 수선 목록을 다 지우고 빈 장바구니일경우 resultMap에 empty값 추가 후 true 반환
            resultMap.put("empty", true);
        } else {
            Long repairId = laundryRepository.insertRepair(ordersDetailId, repairData, files);// 수선 장바구니 추가하고 repairId 반환
//            Long repairId = laundryRepository.getRepairId(ordersDetailId); // 수선 장바구니 추가된 repairId 가져오기
//            laundryRepository.insertRepairImages(repairId, files); // repairId에 이미지 추가
            int saveFile = fileUploadService.saveFile(files, repairId, FileUploadType.REPAIR);
            System.out.println("saveFile = " + saveFile);


        }
        return true;
    }

    @Override
    public void insertCommon(Long ordersDetailId) {
        laundryRepository.insertCommon(ordersDetailId);
    }

    @Override
    public void insertQuickLaundry(Long ordersDetailId) {
        laundryRepository.insertQuickLaundry(ordersDetailId);
    }

    @Override
    public OrderInfo orderInfo(Model model) {
        OrderInfo orderInfo = new OrderInfo();

        String quick = (String) model.getAttribute("quick");
        orderInfo.setQuick("fast".equals(quick));

        List<String> service = (List<String>) model.getAttribute("service");
        if (service == null) return orderInfo;

        orderInfo.setDry(service.contains("dry"));
        orderInfo.setCommon(service.contains("common"));
        orderInfo.setRepair(service.contains("repair"));
        System.out.println("service = " + service);
        System.out.println("orderInfo = " + orderInfo);
        return orderInfo;
    }
    @Override
    public void removeRepair(Long ordersDetailId) {
        List<Repair> repairIdAll = laundryRepository.getRepairId(ordersDetailId); // 수선 이미지 파일, DB 삭제
        if (repairIdAll != null) {
            removeRepairImages(repairIdAll);
        }
        laundryRepository.removeRepair(ordersDetailId);
    }

    @Override
    public Optional<Member> checkAddress(Long memberId) {
        return memberRepository.findById(memberId);
    }

    @Override
    public OrdersDetail createOrdersDetail(Long memberId) {
        return laundryRepository.createOrdersDetail(memberId);
    }

    @Override
    public void checkOrder(Long ordersDetailId, String option, List<String> service) {
        if (!"fast".equals(option)){
            laundryRepository.removeQuickLaundry(ordersDetailId);
            log.info("목록에 없는 빠른배송 옵션 제거");
        }
        if (!service.contains("common")) {
            laundryRepository.removeCommon(ordersDetailId);
            log.info("목록에 없는 생활빨래 옵션 제거");
        }
        if (!service.contains("dry")) {
            laundryRepository.removeDryCleaning(ordersDetailId);
            log.info("목록에 없는 드라이클리닝 옵션 제거");
        }
        if (!service.contains("repair")) {
            removeRepair(ordersDetailId);
            log.info("목록에 없는 수선 옵션 제거");
        }
    }


    @NotNull
    private Orders getOrders(Long memberId, OrderPost orderPost) {
        Orders orders = new Orders();
        orders.setMemberId(memberId);
        orders.setOrdersAddress(orderPost.getAddress());
        orders.setOrdersAddressDetails(orderPost.getAddressDetails());
        orders.setOrdersPickup(orderPost.getLocation());
        orders.setOrdersPickupDate(getDateString(orderPost.getTakeDate()));
        orders.setOrdersReturnDate(getDateString(orderPost.getDeliveryDate()));
        orders.setOrdersInfo(orderPost.getPassword() == null || orderPost.getPassword().equals("") ? null : orderPost.getPassword());
        orders.setOrdersRequest(orderPost.getRequest());
        return orders;
    }

    public String getDateString(LocalDateTime dateTime) {
        if (dateTime == null) return null;
        String date = dateTime.toString();
        return date.substring(0, date.indexOf(":") + 3).replaceAll("T", " ");
    }


}
