package aug.laundry.service.login;

import aug.laundry.domain.*;
import aug.laundry.dto.MemberDto;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.util.Optional;

@Service
public interface LoginService {

    void naverLogin(HttpServletRequest request, Model model, HttpSession session);

    void kakaoProcess(String code, HttpSession session);

    Optional<? extends MemberParent> login(Member member);

    int keepLogin(String sessionId, Date limit, Long memberId);

    Optional<Member> checkUserWithSessionId(String sessionId);

    int renewLoginTime (Long memberId);


}
