package aug.laundry.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "COUPON_LIST")
@SequenceGenerator(name = "SEQ_COUPON_LIST", sequenceName = "SEQ_COUPON_LIST")
@EntityListeners(AuditingEntityListener.class)
public class CouponList {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_COUPON_LIST")
    private Long couponListId;
    private Long memberId;
    private Long couponId;

    @CreatedDate
    private LocalDateTime couponListCreateDate;
    private Integer couponListStatus;
    private Long ordersId;
    private LocalDateTime couponEndDate;

}
