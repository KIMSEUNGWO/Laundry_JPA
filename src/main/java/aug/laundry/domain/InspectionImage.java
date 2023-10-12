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
@Table(name = "INSPECTION_IMAGE")
@SequenceGenerator(name = "SEQ_INSPECTION_IMAGE", sequenceName = "SEQ_INSPECTION_IMAGE")
public class InspectionImage {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_INSPECTION_IMAGE")
    private Long inspectionImageId;
    private Long inspectionId;
    private String inspectionImageUploadName;
    private String inspectionImageStoreName;

}
