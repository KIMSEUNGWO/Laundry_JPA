package aug.laundry.controller.laundry;

import aug.laundry.commom.SessionConstant;
import aug.laundry.dto.OrderDrycleaning;
import aug.laundry.enums.category.CategoryPriceCalculator;
import aug.laundry.enums.category.MemberShip;
import aug.laundry.enums.category.Pass;
import aug.laundry.service.MainService;
import aug.laundry.service.laundry.DrycleaningService;
import aug.laundry.service.login.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/laundry")
public class DrycleaningController {

    private final DrycleaningService drycleaningService;
    private final MainService mainService;
    private final MemberService memberService;


    @GetMapping("/dry")
    public String drycleaning(@SessionAttribute(name = SessionConstant.LOGIN_MEMBER, required = false) Long memberId,
                              @SessionAttribute(name = SessionConstant.ORDERS_CONFIRM, required = false) Long orderDetailId,
                              Model model) {

        // 카테고리
        Map<String, Map<String, Long>> category = mainService.getCategory();
        MemberShip memberShip = memberService.isPass(memberId);
        model.addAttribute("category", category);
        if (memberShip.getCheck() == Pass.PASS) {
            model.addAttribute("memberShip", memberShip);
            model.addAttribute("percent", CategoryPriceCalculator.PASS.percent());
        }

        // 현재 장바구니에서 기록이 있는지 확인후 장바구니 가져오기
        List<OrderDrycleaning> reload = drycleaningService.reloadDrycleaning(orderDetailId); // null 이면 이전에 저장된값 없음
        System.out.println("reload = " + reload);
        model.addAttribute("reload", reload);
        return "project_order_2_1";
    }

    @Transactional
    @PostMapping("/dry/order")
    public @ResponseBody Map<String, Boolean> dryOrder(@SessionAttribute(name = SessionConstant.LOGIN_MEMBER, required = false) Long memberId,
                                                       @SessionAttribute(name = SessionConstant.ORDERS_CONFIRM, required = false) Long ordersDetailId,
                                                       @RequestBody Map<String, Integer> result) {
        HashMap<String, Boolean> resultMap = new HashMap<>();

        // memberId, ordersDetailId == null 이거나 result안에 있는 카테고리명이 Enum 카테고리에 존재하지않는경우 false 반환
        boolean status = drycleaningService.valid(memberId, ordersDetailId, result);
        if (!status){
            resultMap.put("status", false);
            return resultMap;
        }

        // 기존 drycleaning 장바구니 삭제
        drycleaningService.removeDrycleaning(ordersDetailId);

        // 새로운 drycleaning 장바구니 생성, 없으면 생성하지않고 return;
        drycleaningService.insertDryclenaing(ordersDetailId, result, resultMap);

        System.out.println("status = " + status);
        resultMap.put("result", status);
        return resultMap;
    }


}
