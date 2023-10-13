package aug.laundry.dao.member;

import aug.laundry.commom.BCrypt;
import aug.laundry.jpaRepository.JpaMemberRepository;
import aug.laundry.dao.login.LoginMapper;
import aug.laundry.domain.*;
import aug.laundry.dto.ConfirmIdDto;
import aug.laundry.enums.category.Pass;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static aug.laundry.domain.QAdmin.*;
//import static aug.laundry.domain.QMember.*;
import static aug.laundry.domain.QMember.*;
import static aug.laundry.domain.QMember.member;
import static aug.laundry.domain.QQuickRider.*;
import static aug.laundry.domain.QRider.*;

@Repository
@Slf4j
@Transactional
public class MemberRepositoryV2 implements MemberRepository {

    private final LoginMapper loginMapper; // mybatis
    private final JpaMemberRepository jpaMemberRepository; // jpa
    private final JPAQueryFactory query;
    private final BCrypt bc;

    public MemberRepositoryV2(LoginMapper loginMapper, JpaMemberRepository jpaMemberRepository, BCrypt bc, EntityManager em) {
        this.loginMapper = loginMapper;
        this.jpaMemberRepository = jpaMemberRepository;
        this.query = new JPAQueryFactory(em);
        this.bc = bc;
    }

    @Override
    public Optional<Member> findById(Long memberId) {
        return jpaMemberRepository.findById(memberId);
    }

    @Override
    public Optional<Member> findById(String sessionId) {
        return jpaMemberRepository.findBySessionId(sessionId);
    }


    @Override
    public Optional<? extends MemberParent> findByMemberAccount(String memberAccount) {
        Optional<Member> getMember = getMember(memberAccount);
        if (getMember.isPresent()) return getMember;

        Optional<Rider> getRider = getRider(memberAccount);
        if (getRider.isPresent()) return getRider;

        Optional<QuickRider> getQuickRider = getQuickRider(memberAccount);
        if (getQuickRider.isPresent()) return getQuickRider;

        Optional<Admin> getAdmin = getAdmin(memberAccount);
        if (getAdmin.isPresent()) return getAdmin;

        return Optional.empty();
    }


    @Override
    public List<Member> checkId(ConfirmIdDto confirm) {
        return query.select(member)
                .from(member)
                .where(memberName(confirm.getMemberName()), memberPhone(confirm.getMemberPhone()), member.memberDeleteStatus.eq('N'), memberAccount(confirm.getMemberAccount()))
                .fetch();
    }

    private BooleanExpression memberPhone(String memberPhone) {
        return memberPhone == null ? null : member.memberPhone.eq(memberPhone);
    }

    private BooleanExpression memberName(String memberName) {
        return memberName == null ? null : member.memberName.eq(memberName);
    }

    private BooleanExpression memberAccount(String memberAccount) {
        return memberAccount == null ? null : member.account.eq(memberAccount);
    }


    @Override
    public Member registerUser(Member member) {
        // 비밀번호 암호화
        String password = bc.encodeBCrypt(member.getPassword());
        member.setPassword(password);

        // 핸드폰 번호 형식 수정(예 : 010-1234-5678 -> 01012345678)
        String memberPhone = member.getMemberPhone().replace("-","");
        member.setMemberPhone(memberPhone);

        // 추천인 코드 생성후 DB에 저장
        String inviteCode = getInviteCode();
        member.setMemberMyInviteCode(inviteCode);

        Member saveMember = jpaMemberRepository.save(member);
        return saveMember;
    }

    @Override
    public void update(Long memberId, Member updateMember) {
        Member findMember = jpaMemberRepository.findById(memberId).orElseThrow();

        if (updateMember.getPassword() != null){
            findMember.setPassword(bc.encodeBCrypt(updateMember.getPassword()));
        }
        if (updateMember.getMemberAddress() != null){
            findMember.setMemberAddress(updateMember.getMemberAddress());
        }
        if (updateMember.getMemberZipcode() != null){
            findMember.setMemberZipcode(updateMember.getMemberZipcode());
        }
        if (updateMember.getMemberAddressDetails() != null){
            findMember.setMemberAddressDetails(updateMember.getMemberAddressDetails());
        }
    }

    @Override
    public void insert(Member member) {
        jpaMemberRepository.save(member);
    }

    @Override
    public Long findRecommender(String inviteCode) {
        Optional<Member> byMemberMyInviteCode = jpaMemberRepository.findByMemberMyInviteCode(inviteCode);
        if (byMemberMyInviteCode.isPresent()) {
            return byMemberMyInviteCode.get().getMemberId();
        } else {
            return null;
        }

    }

    @Override
    public boolean getPhoneCnt(String phoneNumber) {
        Long resultCount = query.select(member)
                .from(member)
                .where(member.memberPhone.eq(phoneNumber), member.memberSocial.isNull())
                .fetchCount();

        return resultCount != null && resultCount != 0;
    }

    @Override
    public String getInviteCode() {
        // 신규유저의 초대코드 생성
        int length = 8; // 생성할 문자열의 길이
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String inviteCode = "";

        Random random = new Random();

        while(true){
            StringBuilder randomCode = new StringBuilder();
            for (int i = 0; i < length; i++) {

                int randomIndex = random.nextInt(characters.length());
                char randomChar = characters.charAt(randomIndex);
                randomCode.append(randomChar);

            }
            inviteCode = randomCode.toString();

            if (inviteCodeCheck(inviteCode)) {
                System.out.println("초대코드 생성 : " + inviteCode);
                break; // 중복이 아닌 경우 반복문 종료
            }
        }
        return inviteCode;
    }

    @Override
    public boolean inviteCodeCheck(String inviteCode){
        return jpaMemberRepository.findByMemberMyInviteCode(inviteCode).isEmpty();
    }

    @Override
    public Pass isPass(Long memberId) {
        Optional<Member> findMember = jpaMemberRepository.findById(memberId);
        return findMember.isPresent() ? Pass.PASS : Pass.COMMON;
    }


    private Optional<Member> getMember(String memberAccount){
        return Optional.ofNullable(query.select(member).from(member).where(member.account.eq(memberAccount), member.memberDeleteStatus.eq('N')).fetchFirst());
    }

    private Optional<Admin> getAdmin(String memberAccount){
        return Optional.ofNullable(query.select(admin).from(admin).where(admin.account.eq(memberAccount)).fetchFirst());
    }

    private Optional<Rider> getRider(String memberAccount){
        return Optional.ofNullable(query.select(rider).from(rider).where(rider.account.eq(memberAccount)).fetchFirst());
    }

    private Optional<QuickRider> getQuickRider(String memberAccount){
        return Optional.ofNullable(query.select(quickRider).from(quickRider).where(quickRider.account.eq(memberAccount)).fetchFirst());
    }

}
