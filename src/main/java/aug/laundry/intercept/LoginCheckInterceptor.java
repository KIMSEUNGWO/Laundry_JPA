package aug.laundry.intercept;

import aug.laundry.commom.SessionConstant;
import aug.laundry.domain.Member;
import aug.laundry.dto.MemberDto;
import aug.laundry.service.login.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.WebUtils;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class LoginCheckInterceptor implements HandlerInterceptor {

    private final LoginService loginService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("=================인터셉터 실행=================================");
        String requestURI = request.getRequestURI();
        log.info("인증 체크 인터셉터 실행 {}", requestURI);
        HttpSession session = request.getSession(true);

        if (session == null || session.getAttribute(SessionConstant.LOGIN_MEMBER) == null) {
            Cookie loginCookie = WebUtils.getCookie(request,"loginCookie");
            if(loginCookie != null){
                String sessionId = loginCookie.getValue();
                Optional<Member> memberDto = loginService.checkUserWithSessionId(sessionId);
                if(memberDto.isPresent()){
                    session.setAttribute(SessionConstant.LOGIN_MEMBER, memberDto.get().getMemberId());
                    return true;
                }
            }else{
                log.info("인증되지않은 사용자 요청");
                response.sendRedirect("/login?redirectURL=" + requestURI);
                return false;
            }
        }
        log.info("인증된 사용자");
        return true;


    }

}


