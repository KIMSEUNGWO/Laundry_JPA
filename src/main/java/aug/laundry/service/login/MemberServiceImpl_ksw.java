package aug.laundry.service.login;

import aug.laundry.dao.member.MemberDao;
import aug.laundry.dao.member.MemberRepository;
import aug.laundry.dao.point.PointDao;
import aug.laundry.domain.Member;
import aug.laundry.domain.MemberParent;
import aug.laundry.dto.ConfirmIdDto;
import aug.laundry.enums.category.MemberShip;
import aug.laundry.service.ApiExamMemberProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl_ksw implements MemberService {

    private final MemberRepository memberRepository;
    private final MemberDao memberDao;

    private final ApiExamMemberProfile apiExam;

    private final PointDao pointDao;

    public Member selectOne(Long memberId){
        Optional<Member> findMember = memberRepository.findById(memberId);
        if (findMember.isPresent()) {
            return findMember.get();
        } else {
            return null;
        }
    }

    @Override
    public Member selectId(String memberAccount) {
        Optional<? extends MemberParent> byMemberAccount = memberRepository.findByMemberAccount(memberAccount);
        if (byMemberAccount.isPresent() && byMemberAccount.get() instanceof Member) {
            return (Member) byMemberAccount.get();
        }
        return null;
    }

    public void checkId(String memberAccount, Map<String, Object> msg){
        List<Member> members = memberRepository.checkId(new ConfirmIdDto(null, null, memberAccount));
        msg.put("res", 0); // 이후에 수정
        if (members == null || members.isEmpty()){
            msg.put("msg", "사용가능한 아이디입니다.");
        } else {
            msg.put("msg", "아이디가 중복됩니다.");
        }
    }

    public void registerUser(Member memberDto){

        // 회원가입
        Member registerMember = memberRepository.registerUser(memberDto);

        // 회원가입 포인트 적립
        pointDao.addPoint(registerMember.getMemberId(), 1000, "회원가입");

        // 추천인 코드를 작성한 경우 신규회원, 추천한 회원에게 포인트 적립
        if(registerMember.getMemberInviteCode() != null && !registerMember.getMemberInviteCode().isBlank()){
            System.out.println("추천인 코드 : " + registerMember.getMemberInviteCode());
            try {
                // 추천인 포인트 적립
                Long recommanderId = memberRepository.findRecommender(memberDto.getMemberInviteCode());
                pointDao.addPoint(recommanderId, 5000, "신규회원에게 추천");
                // 뉴비 포인트 적립
                pointDao.addPoint(registerMember.getMemberId(), 5000, "추천인 코드 작성");
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    public List<Member> confirmId(String memberName, String memberPhone, String memberAccount){
        // 전화번호 형식변경 후 아이디 list에 담기
        // String memberName, String memberPhone, String memberAccount
        String phoneNumber = memberPhone.replaceAll("-","");
        return memberRepository.checkId(new ConfirmIdDto(memberName, phoneNumber, memberAccount));
    }


    public Long updatePassword(Long memberId, Member member){
        // 비밀번호 암호화
        memberRepository.update(memberId, member);

        return memberId;
    }

    @Override
    public int giveCoupon(Long memberId, Long couponId) {
        return memberDao.giveCoupon(memberId, couponId);
    }

    @Override
    public void updateAddress(Long memberId, Member updateMember){
        memberRepository.update(memberId, updateMember);
    }

    @Override
    public boolean getPhoneCnt(String memberPhone){
        return memberRepository.getPhoneCnt(memberPhone);
    }

    @Override
    public boolean inviteCodeCheck(String inviteCode) {
        return memberRepository.inviteCodeCheck(inviteCode);
    }

    @Override
    public MemberShip isPass(Long memberId) {
        return new MemberShip(memberRepository.isPass(memberId));
    }


}
