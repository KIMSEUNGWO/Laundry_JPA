package aug.laundry.dao.login;

import aug.laundry.dao.jpaRepository.JpaMemberRepository;
import aug.laundry.domain.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.sql.Date;

import static aug.laundry.domain.QMember.*;
import static aug.laundry.domain.QSocialUniqueNum.socialUniqueNum;

@Transactional
@Repository
@Slf4j
public class LoginRepository{

    private final LoginMapper loginMapper;
    private final JpaMemberRepository jpaMemberRepository;
    private final JPAQueryFactory query;

    public LoginRepository(LoginMapper loginMapper, JpaMemberRepository jpaMemberRepository, EntityManager em) {
        this.loginMapper = loginMapper;
        this.jpaMemberRepository = jpaMemberRepository;
        this.query = new JPAQueryFactory(em);
    }

    public Long checkSocialId(String id){
        return query.select(socialUniqueNum.count())
                .from(socialUniqueNum)
                .where(socialUniqueNum.socialUid.eq(id))
                .fetchFirst();
    }

    public Member registerSocialUser(Member memberDto) {
        return jpaMemberRepository.save(memberDto);
    }

    public void registerSocialNumber(Long memberId, String id){
        SocialUniqueNum saveSocialUniqueNum = new SocialUniqueNum();
        saveSocialUniqueNum.setSocialUid(id);
        saveSocialUniqueNum.setMemberId(memberId);
        query.insert(socialUniqueNum).values(saveSocialUniqueNum);
    }

    public Member socialLogin(String memberAccount, String memberSocial){
        return query.select(member)
                .from(member)
                .where(member.account.eq(memberAccount), member.memberSocial.eq(memberSocial))
                .fetchFirst();
    }


    public int keepLogin(@Param("sessionId") String sessionId, @Param("limit") Date limit, @Param("memberId") Long memberId){
        return loginMapper.keepLogin(sessionId, limit, memberId);
    }

    @Transactional
    public Member checkUserWithSessionId(String sessionId){
        return jpaMemberRepository.findBySessionId(sessionId).get();
    }

    public int renewLoginTime (Long memberId){return loginMapper.renewLoginTime(memberId);}
}
