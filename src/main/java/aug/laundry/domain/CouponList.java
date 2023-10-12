package aug.laundry.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "COUPON_LIST")
@SequenceGenerator(name = "SEQ_COUPON_LIST", sequenceName = "SEQ_COUPON_LIST")
public class CouponList {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_COUPON_LIST")
    private Long couponListId;
    private Long memberId;
    private Long couponId;
    private String couponListCreateDate;
    private Integer couponListStatus;
    private Long ordersId;
    private String couponEndDate;

}
