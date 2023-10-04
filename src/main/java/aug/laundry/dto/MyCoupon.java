package aug.laundry.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.NumberFormat;

@Getter
@Setter
@ToString
public class MyCoupon {
    // 쿠폰함에 사용되는 DTO

    private Long couponListId;
    private String couponPrice;
    private String couponName;
    private String couponEndDate;
    private String remainingTime;
}
