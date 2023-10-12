package aug.laundry.dao.laundry;

import aug.laundry.dao.jpaRepository.JpaOrdersDetailRepository;
import aug.laundry.domain.*;
import aug.laundry.dto.*;
import aug.laundry.enums.category.Category;
import aug.laundry.enums.category.Pass;
import aug.laundry.enums.repair.RepairCategory;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import java.io.File;
import java.net.URLDecoder;
import java.util.*;
import java.util.stream.Collectors;

import static aug.laundry.domain.QCommonLaundry.commonLaundry;
import static aug.laundry.domain.QDrycleaning.drycleaning;
import static aug.laundry.domain.QOrdersDetail.*;
import static aug.laundry.domain.QQuickLaundry.quickLaundry;
import static aug.laundry.domain.QRepair.repair;
import static aug.laundry.domain.QRepairImage.repairImage;

@Slf4j
@Repository
@Transactional
public class LaundryRepository {

    private final LaundryMapper laundryMapper;
    private final JpaOrdersDetailRepository jpaOrdersDetailRepository;
    private final JPAQueryFactory query;

    public LaundryRepository(LaundryMapper laundryMapper, JpaOrdersDetailRepository jpaOrdersDetailRepository, EntityManager em) {
        this.laundryMapper = laundryMapper;
        this.jpaOrdersDetailRepository = jpaOrdersDetailRepository;
        this.query = new JPAQueryFactory(em);
    }

    @Value("${repair.images}")
    private String directory;


    public OrderInfo firstInfo(Long memberId, Long ordersDetailId) {
        OrderInfo orderInfo = new OrderInfo();

        OrderInfoDB orderInfoDB = laundryMapper.firstInfo(memberId, ordersDetailId);
        orderInfo.setQuick(orderInfoDB.getIsQuick() != null);
        orderInfo.setDry(orderInfoDB.getIsDry() != null);
        orderInfo.setCommon(orderInfoDB.getIsCommon() != null);
        orderInfo.setRepair(orderInfoDB.getIsDry() != null);
        return orderInfo;
    }

    public List<MyCoupon> getCoupon(Long memberId) {
        return laundryMapper.getCoupon(memberId);
    }


    public Address getAddress(Long memberId) {
        return laundryMapper.getAddress(memberId);
    }

    public List<Category> getDry(Long memberId, Long ordersDetailId) {
        return laundryMapper.getDry(memberId, ordersDetailId).stream().map(x -> Category.valueOf(x)).collect(Collectors.toList());
    }

    public List<RepairCategory> getRepair(Long memberId, Long ordersDetailId) {
        return laundryMapper.getRepair(memberId, ordersDetailId).stream().map(x -> RepairCategory.valueOf(x)).collect(Collectors.toList());
    }


    public boolean validCoupon(Long memberId, Long couponListId) {
        if (couponListId == null) return false;
        CouponList couponList = laundryMapper.validCoupon(memberId, couponListId);
        if (couponList == null || !couponList.getMemberId().equals(memberId) || !couponList.getCouponListId().equals(couponListId) || couponList.getCouponListStatus() != 1L) {
            return false;
        }
        return true;
    }

    public Integer useCoupon(Long memberId, Long couponListId, Long ordersId) {
        return laundryMapper.useCoupon(memberId, couponListId, ordersId);
    }


    public void insert(Orders orders) {
        laundryMapper.insert(orders);
    }

    public Long getCouponDiscount(Long memberId, Long couponListId) {
        return laundryMapper.getCouponDiscount(memberId, couponListId);
    }

    public Long check(Long memberId, Long ordersDetailId) {
        Long findOrdersDetailId = query.select(ordersDetail.ordersDetailId)
                .from(ordersDetail)
                .where(ordersDetail.memberId.eq(memberId), ordersDetail.ordersId.isNull(), ordersDetailId(ordersDetailId))
                .fetchFirst();
        log.info("orders_detail_id = {}", findOrdersDetailId);
        return findOrdersDetailId;
    }

    private BooleanExpression ordersDetailId(Long ordersDetailId) {
        if (ordersDetailId != null) {
            return ordersDetail.ordersDetailId.eq(ordersDetailId);
        }
        return null;
    }

    public void removeOrdersDetail(Long ordersDetailId) {
        query.delete(ordersDetail).where(ordersDetail.ordersDetailId.eq(ordersDetailId)).execute();
        log.info("Removed OrdersDetail");
    }

    public void removeRepairImageFile(List<RepairImage> repairImageStoreNames) {
        // 실제저장된 Repair Image 파일 삭제
        for (RepairImage repairImage : repairImageStoreNames) {
            String repairImageStoreName = repairImage.getRepairImageStoreName();
            if (repairImageStoreName != null) {
                String srcFileName = URLDecoder.decode(directory + repairImageStoreName);
                File file = new File(srcFileName);
                boolean delete = file.delete();
                log.info("Removed srcFileName = {}", srcFileName);
            }
        }
    }

    public OrdersDetail createOrdersDetail(Long memberId) {
        OrdersDetail newOrdersDetail = new OrdersDetail();
        newOrdersDetail.setMemberId(memberId);
        OrdersDetail saveOrdersDetail = jpaOrdersDetailRepository.save(newOrdersDetail);
        return saveOrdersDetail;
    }

    public void removeDryCleaning(Long ordersDetailId) {
        query.delete(drycleaning).where(drycleaning.ordersDetailId.eq(ordersDetailId)).execute();
        log.info("Removed Drycleaning");
    }

    public void removeRepair(Long ordersDetailId) {
        query.delete(repair).where(repair.ordersDetailId.eq(ordersDetailId)).execute();
        log.info("Removed Repair");
    }
    public void removeCommon(Long ordersDetailId) {
        query.delete(commonLaundry).where(commonLaundry.ordersDetailId.eq(ordersDetailId)).execute();
        log.info("Removed Common");
    }


    public List<OrderRepair> reloadRepair(Long orderDetailId) {
        return laundryMapper.reloadRepair(orderDetailId);
    }

    public List<RepairImage> getRepairImage(Long repairId) {
        return query.select(repairImage)
                .from(repairImage)
                .where(repairImage.repairId.eq(repairId))
                .fetch();
    }

    public Long insertRepair(Long ordersDetailId, Map<String, RepairFormData> repairData, List<MultipartFile> files) {
        for (String s : repairData.keySet()) { // s = 1 ~ 시작되는 숫자
            InsertRepairDto insertRepairDto = new InsertRepairDto();
            insertRepairDto.setOrdersDetailId(ordersDetailId);
            insertRepairDto.setRequest(repairData.get(s).getRequest());
            insertRepairDto.setCategory(repairData.get(s).getTitle());
            laundryMapper.insertRepair(insertRepairDto);
            log.info("InsertRepairDto.getRepairId = {}", insertRepairDto.getRepairId());
            return insertRepairDto.getRepairId();
        }
        return null;
    }

    public List<Repair> getRepairId(Long ordersDetailId) {
        return query.select(repair)
                .from(repair)
                .where(repair.ordersDetailId.eq(ordersDetailId))
                .fetch();
    }

    public void insertCommon(Long ordersDetailId) {
        laundryMapper.insertCommon(ordersDetailId);
    }

    public void insertQuickLaundry(Long ordersDetailId) {
        laundryMapper.insertQuickLaundry(ordersDetailId);
    }

    public void updateOrdersDetail(Long ordersId, Long ordersDetailId) {
        laundryMapper.updateOrdersDetail(ordersId, ordersDetailId);
    }

    public void insertInspection(Long ordersId) {
        laundryMapper.insertInspection(ordersId);
    }

    public List<Long> findByRepairId(Long ordersDetailId) {
        return laundryMapper.findByRepairId(ordersDetailId);
    }

    public void removeRepairImages(Long repairId) {
        query.delete(repairImage).where(repairImage.repairId.eq(repairId)).execute();
        log.info("Removed RepairImage");
    }

   public List<MyCoupon> getCoupon2(Long memberId) {
        List<MyCoupon> coupon = laundryMapper.getCoupon2(memberId);
        log.info("getCoupon2={}", coupon);
        return coupon;
    }

    public void removeQuickLaundry(Long ordersDetailId) {
        query.delete(quickLaundry)
                .where(quickLaundry.ordersDetailId.eq(ordersDetailId)).execute();
        log.info("Removed QuickLaundry");
    }

}
