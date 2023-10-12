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
@Table(name = "POINT")
@SequenceGenerator(name = "SEQ_POINT", sequenceName = "SEQ_POINT")
public class Point {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_POINT")
    private Long pointId;
    private Long memberId;
    private Integer pointStack;
    private Integer pointNow;
    private String pointStackReason;
    private String pointStackDate;


}

