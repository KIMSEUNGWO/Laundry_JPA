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
@Table(name = "PAYMENTINFO")
@SequenceGenerator(name = "SEQ_PAYMENTINFO", sequenceName = "SEQ_PAYMENTINFO_ID")
public class Paymentinfo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PAYMENTINFO")
    private Long paymentinfoId;
    private Long memberId;
    private String impUid;
    private String payMethod;
    private String merchantUid;
    private String buyerName;
    private String buyerTel;
    private Long amount;
    private Integer paymentStatus;
    private String refundReason;

    public Paymentinfo(Long memberId, String impUid, String payMethod, String merchantUid, String buyerName, String buyerTel, Long amount) {
        this.memberId = memberId;
        this.impUid = impUid;
        this.payMethod = payMethod;
        this.merchantUid = merchantUid;
        this.buyerName = buyerName;
        this.buyerTel = buyerTel;
        this.amount = amount;
    }
}
