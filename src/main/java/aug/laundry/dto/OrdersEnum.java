package aug.laundry.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class OrdersEnum {

    private Long ordersId;
    private Long memberId;
    private String ordersDate;
    private String ordersAddress;
    private String ordersAddressDetails;
    private String ordersInfo;
    private String ordersRequest;
    private Integer ordersPay;
    private String ordersPickup;
    private Integer ordersExpectedPrice;
    private Integer ordersFinalPrice;
    private String ordersStatus;
    private Long riderId;
    private Long quickRiderId;
    private String ordersPickupDate;
    private String ordersReturnDate;

    @Builder
    public OrdersEnum(Long ordersId, Long memberId, String ordersDate, String ordersAddress, String ordersAddressDetails, String ordersInfo, String ordersRequest, Integer ordersPay, String ordersPickup, Integer ordersExpectedPrice, Integer ordersFinalPrice, String ordersStatus, Long riderId, Long quickRiderId, String ordersPickupDate, String ordersReturnDate) {
        this.ordersId = ordersId;
        this.memberId = memberId;
        this.ordersDate = ordersDate;
        this.ordersAddress = ordersAddress;
        this.ordersAddressDetails = ordersAddressDetails;
        this.ordersInfo = ordersInfo;
        this.ordersRequest = ordersRequest;
        this.ordersPay = ordersPay;
        this.ordersPickup = ordersPickup;
        this.ordersExpectedPrice = ordersExpectedPrice;
        this.ordersFinalPrice = ordersFinalPrice;
        this.ordersStatus = ordersStatus;
        this.riderId = riderId;
        this.quickRiderId = quickRiderId;
        this.ordersPickupDate = ordersPickupDate;
        this.ordersReturnDate = ordersReturnDate;
    }
}
