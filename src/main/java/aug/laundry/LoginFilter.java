package aug.laundry;

import aug.laundry.service.login.LoginService;
import lombok.RequiredArgsConstructor;

import javax.servlet.*;
import java.io.IOException;

@RequiredArgsConstructor
public class LoginFilter implements Filter {

    private final LoginService loginService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

//        HttpServletRequest req = (HttpServletRequest) request;
//        Cookie loginCookie = WebUtils.getCookie(req,"loginCookie");

//        if(loginCookie != null){
//            HttpSession session = req.getSession();
//            String sessionId = loginCookie.getValue();
//            MemberDto memberDto = loginService.checkUserWithSessionId(sessionId);
//            if(memberDto != null){
//                session.setAttribute(SessionConstant.LOGIN_MEMBER, memberDto.getMemberId());
//            }else{
//                return;
//            }
//
//        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
