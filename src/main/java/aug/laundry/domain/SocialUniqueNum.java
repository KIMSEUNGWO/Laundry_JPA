package aug.laundry.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "SOCIAL_UNIQUE_NUM")
public class SocialUniqueNum {

    @Id
    @Column(name = "SOCIAL_ID")
    private String socialUid;

    @Column(name = "MEMBER_ID")
    private Long memberId;
}
