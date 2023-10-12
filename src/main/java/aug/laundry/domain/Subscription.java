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
@Table(name = "SUBSCRIPTION")
@SequenceGenerator(name = "SEQ_SUBSCRIPTION", sequenceName = "SEQ_SUBSCRIPTION_ID")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SUBSCRIPTION")
    private Long subscriptionId;
    private String subscriptionName;
    private Integer subscriptionPrice;
    private Integer subscriptionMonth;
    private Integer subscriptionDiscountPercent;

}
