package aug.laundry.dao.mypage;

import aug.laundry.domain.Member;
import aug.laundry.dto.*;
import aug.laundry.jpaRepository.JpaMemberRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
@RequiredArgsConstructor
public class MypageDao {

  private final MypageMapper mypageMapper;
  private final JpaMemberRepository jpaMemberRepository;

  public String findByName(Long memberId){
    Optional<Member> findMember = jpaMemberRepository.findById(memberId);
    if (findMember.isPresent()) {
      return findMember.get().getMemberName();
    }
      return null;
  }

  public MypageDto findByNameAndPass(Long memberId){
    return mypageMapper.findByNameAndPass(memberId);
  }

  public String findByInviteCode(Long memberId){
    Optional<Member> findMember = jpaMemberRepository.findById(memberId);
    if (findMember.isPresent()) {
      return findMember.get().getMemberMyInviteCode();
    }
    return null;
  }

  public MypageDto findByInfo(Long memberId){ return mypageMapper.findByInfo(memberId); }

  public int updateAddress(Long memberId, UpdateAddressDto updateAddressDto) {
    return mypageMapper.updateAddress(memberId, updateAddressDto.getMemberZipcode(), updateAddressDto.getMemberAddress(), updateAddressDto.getMemberAddressDetails());
  }

  public int updatePhone(Long memberId, UpdatePhoneDto updatePhoneDto){
    return mypageMapper.updatePhone(memberId, updatePhoneDto.getMemberPhone());
  }

  public int unregister(Long memberId) { return mypageMapper.unregister(memberId); }

  public int changePassword(Long memberId, ChangePasswordDto changePasswordDto){
    return mypageMapper.changePassword(memberId, changePasswordDto.getMemberPassword());
  }

  public List<MyPointDto> getPoint(Long memberId){ return mypageMapper.getPoint(memberId); }

  public PointNowDto getPointNow(Long memberId){ return mypageMapper.getPointNow(memberId); }

  public int someCoupon(Long memberId){ return mypageMapper.someCoupon(memberId); }

  public MySubscribeMonthsDto findMySubscribeMonths(Long memberId){return mypageMapper.findMySubscribeMonths(memberId); }
}
