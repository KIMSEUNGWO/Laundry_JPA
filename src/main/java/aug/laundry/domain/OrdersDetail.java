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
@Table(name = "ORDERS_DETAIL")
@SequenceGenerator(name = "SEQ_ORDERS_DETAIL", sequenceName = "SEQ_ORDERS_DETAIL_ID")
public class OrdersDetail {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ORDERS_DETAIL")
    private Long ordersDetailId;
    private Long ordersId;
    private Long inspectionId;
    private Long memberId;

}
