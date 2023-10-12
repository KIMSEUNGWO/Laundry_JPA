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
@Table(name = "DRYCLEANING")
public class Drycleaning {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_DRYCLEANING_ID")
    private Long drycleaningId;
    private Long ordersDetailId;
    private String drycleaningRequest;
    private String drycleaningCategory;
    private char drycleaningPossibility;
    private String drycleaningNotReason;


}
