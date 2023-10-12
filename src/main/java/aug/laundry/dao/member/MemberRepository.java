package aug.laundry.dao.member;

import aug.laundry.domain.Member;
import aug.laundry.domain.MemberParent;
import aug.laundry.dto.ConfirmIdDto;
import aug.laundry.enums.category.Pass;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    Optional<Member> findById(Long memberId);
    Optional<Member> findById(String sessionId);
    Optional<? extends MemberParent> findByMemberAccount(String memberAccount);

    List<Member> checkId(ConfirmIdDto confirm);

    void registerUser(Member member);

    void update(Long memberId, Member updateMember);

    void insert(Member member);

    Long findRecommender(String inviteCode);

    boolean getPhoneCnt(String phoneNumber);


    String getInviteCode();
    boolean inviteCodeCheck(String inviteCode);

    Pass isPass(Long memberId);
}
