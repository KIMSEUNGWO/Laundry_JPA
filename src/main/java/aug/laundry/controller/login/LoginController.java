package aug.laundry.controller.login;

import aug.laundry.commom.SessionConstant;
import aug.laundry.domain.*;
import aug.laundry.dto.*;
import aug.laundry.service.login.LoginService;
import aug.laundry.service.login.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Controller
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {

    private final LoginService loginService;
    private final MemberService memberService;

    // 테스트입니다.
    @GetMapping("/naver_callback")
    public String naverLogin_callback(HttpServletRequest request, Model model, HttpSession session, String state) {
        loginService.naverLogin(request, model, session);
        System.out.println("naverLogin =======");
        System.out.println("naver sessionId : " + session.getAttribute("memberId"));
        Long memberId = (Long)session.getAttribute("memberId");
        Member member = memberService.selectOne(memberId);

        // 탈퇴한 회원인지 확인
        try {
            char isDelete = memberService.selectOne(memberId).getMemberDeleteStatus();
            if(isDelete == 'Y'){
                model.addAttribute("errorMsg", "이미 탈퇴한 회원입니다.");
                return "project_login";
            }
        }catch (NullPointerException e){
            return "project_login";
        }
        
        // 만약 주소가 저장되어 있지 않으면 주소를 등록하는 페이지로 이동
        if(member.getMemberZipcode() == null){
            return "redirect:/login/social/address";
        }

        if(state != null && !"null".equals(state)){
            return "redirect:" + state;
        }

        return "redirect:/";
    }
    @GetMapping("/kakaoLogin")
    public String kakaoLogin_Redirect(String code, Model model, HttpSession session, String state){
        loginService.kakaoProcess(code, session);
        Long memberId = (Long)session.getAttribute("memberId");
        Member memberDto = memberService.selectOne(memberId);
        // 탈퇴한 회원인지 확인
        try {
            char isDelete = memberService.selectOne(memberId).getMemberDeleteStatus();
            if(isDelete == 'Y'){
                model.addAttribute("errorMsg", "이미 탈퇴한 회원입니다.");
                return "project_login";
            }
        }catch (NullPointerException e){
            return "project_login";
        }

        if(memberDto.getMemberZipcode() == null){
            return "redirect:/login/social/address";
        }

        // 인터셉터에서 온 redirectURL이 있다면 로그인 후 redirectURL의 경로로 이동
        String redirectURL = state;
        if(redirectURL != null && !redirectURL.isEmpty()){
            model.addAttribute("msg", "주소를 등록해주세요!");
            return "redirect:" + redirectURL;
        }

        return "redirect:/";
    }

    @GetMapping("/social/address")
    public String socialRegisterAddress(@SessionAttribute(name = SessionConstant.LOGIN_MEMBER, required = false) Long memberId, Model model){
        model.addAttribute("memberId", memberId);
        return "project_social_address";
    }

    @PostMapping("/social/address")
    public String socialUpdateAddress(Member member, Model model, RedirectAttributes redirectAttributes){
        memberService.updateAddress(member.getMemberId(), member);
        System.out.println("소셜 멤버 주소 업데이트 : " + member);

        // 최근 로그인 기록이 있으면 true 없으면 false
        redirectAttributes.addFlashAttribute("firstLogin", member.getMemberRecentlyDate() == null ? "Y" : "N");

        loginService.renewLoginTime(member.getMemberId());
        return "redirect:/";
    }


    @PostMapping("/update/address")
    public String updateAddress(){
        return "redirect:/";
    }


    @PostMapping("/loginAction")
    public  String login(Member member, HttpSession session, Model model, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        Optional<? extends MemberParent> optionalMemberParent = loginService.login(member);

        if (optionalMemberParent.isEmpty()) {
            model.addAttribute("errorMsg", "아이디 또는 비밀번호를 잘못 입력했습니다.");
            return "project_login";
        }
        String redirectURL = request.getParameter("redirectURL");
        MemberParent memberParent = optionalMemberParent.get();

        if (memberParent instanceof Member){
            Member getMember = (Member) memberParent;

            if (getMember.getMemberRecentlyDate() == null) memberService.giveCoupon(getMember.getMemberId(), 1L); // 웰컴쿠폰 지급

            // 최근 로그인 기록이 있으면 true 없으면 false
            redirectAttributes.addFlashAttribute("firstLogin", getMember.getMemberRecentlyDate() == null ? "Y" : "N");

            // 최근 로그인 시간 갱신
            loginService.renewLoginTime(getMember.getMemberId());

            // 세션에 memberId 저장
            session.setAttribute(SessionConstant.LOGIN_MEMBER, getMember.getMemberId());

            int amount = 60 * 60 * 24 * 7;
            setCookie(session, response, amount); // 쿠키설정

            Date limit = new Date(System.currentTimeMillis() + (1000*amount));
            // 현재 세션 id와 유효시간을 사용자 테이블에 저장한다.
            loginService.keepLogin(session.getId(), limit, getMember.getMemberId());

            if(redirectURL != null && !redirectURL.isEmpty()){
                return "redirect:" + redirectURL;
            }
            request.setAttribute("useCookie", request.getParameter("useCookie"));
            return "redirect:/";
        }
        return anotherLogin(session, memberParent);
    }

    private String anotherLogin(HttpSession session, MemberParent memberParent) {
        if (memberParent instanceof Rider){
            session.setAttribute(SessionConstant.LOGIN_MEMBER, ((Rider) memberParent).getRiderId());
            return "redirect:/ride/routine";
        } else if (memberParent instanceof QuickRider){
            session.setAttribute(SessionConstant.LOGIN_MEMBER, ((QuickRider) memberParent).getQuickRiderId());
            return "redirect:/ride/wait";
        } else if (memberParent instanceof Admin){
            Admin adminMember = (Admin) memberParent;
            session.setAttribute(SessionConstant.LOGIN_MEMBER, adminMember.getAdminId());
            return "redirect:/admin/" + adminMember.getAdminId();
        }
        return "redirect:/";
    }

    private static void setCookie(HttpSession session, HttpServletResponse response, int amount) {
        Cookie cookie = new Cookie("loginCookie", session.getId());
        cookie.setPath("/");
        cookie.setMaxAge(amount); // 단위는 (초)임으로 7일정도로 유효시간을 설정해 준다.
        // 쿠키를 적용해 준다.
        response.addCookie(cookie);
    }

    @GetMapping("/find-account")
    public String goFindAccount(){
        return "project_search_id";
    }

    @PostMapping("/find-account")
    public String confirmId(ConfirmIdDto confirmIdDto, Model model){
        List<Member> list = memberService.confirmId(confirmIdDto.getMemberName(), confirmIdDto.getMemberPhone(), confirmIdDto.getMemberAccount());
        model.addAttribute("list", list);
        return "project_search_id_result";
    }


    @GetMapping("/find-pw")
    public String goFindPassword(){
        return "project_search_password";
    }

    @ResponseBody
    @PostMapping("/find-pw")
    public Map<String, Object> confirmId(@RequestBody @Valid ConfirmIdDto confirmIdDto){
        List<Member> list = memberService.confirmId(confirmIdDto.getMemberName(), confirmIdDto.getMemberPhone(), confirmIdDto.getMemberAccount());
        Map<String, Object> map = new HashMap<>();
        map.put("list", list);
        return map;
    }

    @PostMapping("/find-pw/update")
    public String updatePassword(@SessionAttribute(name = SessionConstant.LOGIN_MEMBER) Long memberId, Member member){
        System.out.println("memberDto : " + member);
        Long res = memberService.updatePassword(memberId, member);
        System.out.println("비밀번호 변경(memberId) : " + res);
        return "redirect:/login";
    }


    @GetMapping("/find-pw/update")
    public String updatePasswordForm(@RequestParam("memberAccount") String memberAccount, Model model){
        System.out.println("memberAccount : " + memberAccount);
        model.addAttribute("memberAccount", memberAccount);
        return "project_change_password";
    }


    @GetMapping
    public String goLogin(HttpServletRequest request, @CookieValue(name = "loginCookie", required = false) Cookie loginCookie){
        if(loginCookie != null){
            Optional<Member> findMember = loginService.checkUserWithSessionId(loginCookie.getValue());
            if(findMember.isPresent()){
                HttpSession session = request.getSession();
                session.setAttribute(SessionConstant.LOGIN_MEMBER, findMember.get().getMemberId());
                return "redirect:/";
            }
        }
        return "project_login";
    }


}


















