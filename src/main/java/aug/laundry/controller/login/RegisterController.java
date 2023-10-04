package aug.laundry.controller.login;

import aug.laundry.domain.Member;
import aug.laundry.dto.MemberDto;
import aug.laundry.service.login.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class RegisterController {

    private final MemberService memberService;

    @ResponseBody
    @GetMapping("/id/check/{memberAccount}")
    public Map<String, Object> idCheck(@PathVariable String memberAccount){
        Map<String, Object> msg = new HashMap<>();
        memberService.checkId(memberAccount, msg);
        return msg;
    }

    @RequestMapping(value = "/registerAction", method = {RequestMethod.POST})
    public String registerUser(@Valid Member member, BindingResult bindingResult, Model model) {
        System.out.println(member.toString());
        Map<String, String> validation = new HashMap<>();
        String phonenumber = member.getMemberPhone().replace("-","");
        if(memberService.getPhoneCnt(phonenumber)){
            model.addAttribute("msg", "이미 등록된 휴대폰 번호입니다.");
            return "project_register";
        }
        if (bindingResult.hasErrors()) {
            //throw new IllegalArgumentException("validation 실패");

            System.out.println("spring validation 실패!");
            validation.put("validation", "실패");
            bindingResult.getAllErrors()
                    .forEach(objectError -> {
                        System.err.println("code : " + objectError.getCode());
                        System.err.println("defaultMessage : " + objectError.getDefaultMessage());
                        System.err.println("objectName : " + objectError.getObjectName());
                    });
            return "/register";

        } else {
            validation.put("validation", "성공");
            memberService.registerUser(member);
        }

        return "redirect:/login";

    }

    @ResponseBody
    @GetMapping("/inviteCode/{inviteCode}")
    public Map<String, Object> getInviteCode(@PathVariable String inviteCode){
        boolean result = memberService.inviteCodeCheck(inviteCode);
        Map<String, Object> map = new HashMap<>();
        if(!result){
            map.put("result", 1);
            map.put("resultMsg", "사용가능한 코드입니다.");
        }else{
            map.put("result", 0);
            map.put("resultMsg", "코드를 다시 확인해주세요.");
        }

        return map;
    }

    @GetMapping("/register")
    public String goRegister(){
        return "project_register";
    }


}
