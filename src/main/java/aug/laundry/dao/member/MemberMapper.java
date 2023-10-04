package aug.laundry.dao.member;

import aug.laundry.dto.MemberDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MemberMapper {
    public MemberDto selectOne(Long memberId);
    public int giveCoupon(@Param("memberId")Long memberId, @Param("couponId")Long couponId);

    public int updateAddress(@Param("memberId")Long memberId , @Param("memberZipcode")Integer memberZipcode,@Param("memberAddress")String memberAddress, @Param("memberAddressDetails") String memberAddressDetails);

}
