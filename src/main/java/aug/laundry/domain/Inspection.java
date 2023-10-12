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
@Table(name = "INSPECTION")
@SequenceGenerator(name = "SEQ_INSPECTION", sequenceName = "SEQ_INSPECTION_ID")
public class Inspection {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_INSPECTION")
    private Long inspectionId;
    private Long adminId;
    private char inspectionStatus;
    private Long ordersId;

}
