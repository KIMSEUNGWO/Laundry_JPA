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
@Table(name = "COUPON")
@SequenceGenerator(name = "SEQ_COUPON", sequenceName = "SEQ_COUPON")
public class Coupon{

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_COUPON")
    private Long couponId;
    private String couponName;
    private Integer couponType;
    private Integer couponPrice;
    private Integer couponPeriodOfUse;
    private Long gradeId;

}
