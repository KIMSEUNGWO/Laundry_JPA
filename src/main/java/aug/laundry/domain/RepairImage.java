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
@Table(name = "REPAIR_IMAGE")
@NoArgsConstructor
public class RepairImage {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_REPAIR_IMAGE_ID")
    private Long repairImageId;
    private Long repairId;
    private String repairImageUploadName;
    private String repairImageStoreName;

}
