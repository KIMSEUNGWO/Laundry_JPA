package aug.laundry.dao.login;
import aug.laundry.domain.Admin;
import aug.laundry.domain.Member;
import aug.laundry.domain.QuickRider;
import aug.laundry.domain.Rider;
import aug.laundry.dto.MemberDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Date;

@Mapper
public interface LoginMapper {
    public int checkSocialId(String id);

    public int registerSocialUser(MemberDto memberDto);

    public int registerSocialNumber(String id);

    public MemberDto socialLogin(@Param("memberAccount") String memberAccount, @Param("memberSocial") String memberSocial);

    public int keepLogin(@Param("sessionId") String sessionId, @Param("limit") Date limit, @Param("memberId") Long memberId);

    public int renewLoginTime (Long memberId);



}
