package aug.laundry.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "ORDERS_DETAIL")
public class OrdersDetail {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long ordersDetailId;
    private Long ordersId;
    private Long inspectionId;
    private Long memberId;


    public OrdersDetail() {
    }
}
