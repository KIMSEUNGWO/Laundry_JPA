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
public class QuickLaundry {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long quickLaundryId;
    private String quickLaundryTakeDate;
    private Long ordersDetailId;

}
