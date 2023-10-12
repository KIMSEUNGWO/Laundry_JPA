package aug.laundry.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "QUICK_LAUNDRY")
@NoArgsConstructor
@SequenceGenerator(name = "SEQ_QUICK_LAUNDRY", sequenceName = "SEQ_QUICK_LAUNDRY_ID")
public class QuickLaundry {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_QUICK_LAUNDRY")
    private Long quickLaundryId;
    private String quickLaundryTakeDate;
    private Long ordersDetailId;

}
