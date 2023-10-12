package aug.laundry.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ADMIN")
public class Admin extends MemberParent {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MEMBER")
    private Long adminId;

    @Column(name = "ADMIN_EMAIL")
    private String account;

    @Column(name = "ADMIN_PASSWORD")
    private String password;
    private String adminName;
    private Integer adminPhone;

}
