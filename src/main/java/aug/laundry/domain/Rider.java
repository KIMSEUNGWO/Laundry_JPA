package aug.laundry.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "RIDER")
public class Rider extends MemberParent {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MEMBER")
    private Long riderId;

    @Column(name = "RIDER_EMAIL")
    private String account;

    @Column(name = "RIDER_PASSWORD")
    private String password;

    private String riderName;
    private String riderPhone;
    private Integer riderZipcode;
    private String riderAddress;
    private String riderPossibleZipcode;
    private String workingArea;
}
