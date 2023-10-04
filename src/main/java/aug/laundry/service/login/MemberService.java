package aug.laundry.service.login;

import aug.laundry.domain.Member;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public interface MemberService {
    Member selectOne(Long memberId);

    Member selectId(String memberAccount);

    void checkId(String memberAccount, Map<String, Object> msg);

    void registerUser(Member memberDto);
    List<Member> confirmId(String memberName, String memberPhone, String memberAccount);

    Long updatePassword(Long memberId, Member memberDto);
    int giveCoupon(Long memberId,Long couponId);
    void updateAddress(Long memberId, Member member);
    boolean getPhoneCnt(String memberPhone);
    boolean inviteCodeCheck(String inviteCode);
}
