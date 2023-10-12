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
@Table(name = "ORDERS")
@SequenceGenerator(name = "SEQ_ORDERS", sequenceName = "SEQ_ORDERS")
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ORDERS")
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
    private Integer ordersStatus;
    private Long riderId;
    private Long quickRiderId;
    private String ordersPickupDate;
    private String ordersReturnDate;
    private Long pointId;

}
