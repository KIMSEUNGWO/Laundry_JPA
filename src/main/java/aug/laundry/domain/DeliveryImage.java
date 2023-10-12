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
@Table(name = "DELIVERY_IMAGE")
@SequenceGenerator(name =  "SEQ_DELIVERY_IMAGE", sequenceName = "SEQ_DELIVERY_IMAGE")
public class DeliveryImage {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_DELIVERY_IMAGE")
    private Long deliveryImageId;
    private Long ordersId;
    private String deliveryImageUploadName;
    private String deliveryImageStoreName;

}
