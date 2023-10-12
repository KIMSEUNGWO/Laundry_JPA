package aug.laundry.domain;

import aug.laundry.enums.repair.RepairCategory;
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
@Table(name = "REPAIR")
public class Repair {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_REPAIR_ID")
    private Long repairId;

    private Long ordersDetailId;
    private String repairRequest;
    private RepairCategory repairCategory;
    private Character repairPossibility;
    private String repairNotReason;


}
