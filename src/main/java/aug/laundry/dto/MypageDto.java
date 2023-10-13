package aug.laundry.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MypageDto {

  private Long memberId;
  private String memberName;
  private String memberPhone;
  private String memberZipcode;
  private String memberAddress;
  private Long subscriptionId;
  private String subscriptionExpireDate;
  private Long gradeId;
  private String memberMyInviteCode;
  private String memberAddressDetails;

}
