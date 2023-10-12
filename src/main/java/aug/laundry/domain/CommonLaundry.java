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
@Table(name = "COMMON_LAUNDRY")
@NoArgsConstructor
public class CommonLaundry {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_COMMON_LAUNDRY_ID")
    private Long commonLaundryId;
    private Double commonLaundryWeight;
    private Long ordersDetailId;

}
