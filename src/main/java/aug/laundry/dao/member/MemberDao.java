package aug.laundry.dao.member;

import aug.laundry.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
@RequiredArgsConstructor
public class MemberDao {

    private final MemberMapper memberMapper;

    public int giveCoupon(@Param("memberId")Long memberId, @Param("couponId")Long couponId){
        return memberMapper.giveCoupon(memberId,couponId);
    }

    public int updateAddress(Long memberId, Integer memberZipcode, String memberAddress, String memberAddressDetails){
        return  memberMapper.updateAddress(memberId, memberZipcode, memberAddress, memberAddressDetails);
    }

}
