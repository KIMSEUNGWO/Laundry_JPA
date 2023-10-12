package aug.laundry.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QUICK_RIDER")
public class QuickRider extends MemberParent {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MEMBER")
    private Long quickRiderId;

    @Column(name = "QUICK_RIDER_EMAIL")
    private String account;

    @Column(name = "QUICK_RIDER_PASSWORD")
    private String password;
    private String quickRiderName;
    private String quickRiderPhone;
    private Integer quickRiderZipcode;
    private String quickRiderAddress;

}
