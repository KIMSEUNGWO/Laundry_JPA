package aug.laundry.dao.login;

import aug.laundry.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaMemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findBySessionId(String sessionId);
    Optional<Member> findByMemberMyInviteCode(String inviteCode);

    Optional<Member> findByAccount(String account);



}
