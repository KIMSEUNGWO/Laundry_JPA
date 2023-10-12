package aug.laundry.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import javax.validation.constraints.*;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@Table(name = "MEMBER")
public class Member extends MemberParent {


    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MEMBER")
    private Long memberId;

    @Column(nullable = false, name = "MEMBER_ACCOUNT")
    @NotBlank(message = "memberAccount is required")
    private String account;

    @Column(name = "MEMBER_PASSWORD")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[@#$%^&+=!]).{8,15}$", message = "비밀번호는 대소문자, 숫자, 특수문자를 포함한 8~15자여야 합니다.")
    private String password;

    @Column(nullable = false)
    @NotBlank(message = "memberName is required")
    private String memberName;

    @Column(nullable = false)
    @NotNull(message = "memberPhone is required")
    private String memberPhone;

    @NotNull(message = "memberZipcode is required")
    @Min(value = 0)
    @Max(value = 99999)
    private Integer memberZipcode;

    @NotBlank(message = "memberAddress is required")
    private String memberAddress;

    private String memberCreateDate;
    private String memberSocial;
    private Long subscriptionId;
    private String subscriptionExpireDate;
    private Long gradeId;
    private String memberRecentlyDate;
    private char memberDeleteStatus;

    @Column(nullable = false)
    private String memberMyInviteCode;
    private String memberAddressDetails;
    private String memberInviteCode;
    private String sessionId;
    private String sessionLimit;

}
