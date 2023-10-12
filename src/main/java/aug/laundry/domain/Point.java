package aug.laundry.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "POINT")
@SequenceGenerator(name = "SEQ_POINT", sequenceName = "SEQ_POINT")
@EntityListeners(AuditingEntityListener.class)
public class Point{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_POINT")
    private Long pointId;
    private Long memberId;
    private Integer pointStack;
    private Integer pointNow;
    private String pointStackReason;

    @CreatedDate
    private LocalDateTime pointStackDate;


}

