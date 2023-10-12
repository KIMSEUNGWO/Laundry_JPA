package aug.laundry.controller.laundry;

import aug.laundry.commom.SessionConstant;
import aug.laundry.domain.Member;
import aug.laundry.domain.OrdersDetail;
import aug.laundry.dto.*;
import aug.laundry.enums.category.CategoryPriceCalculator;
import aug.laundry.enums.category.MemberShip;
import aug.laundry.enums.category.Pass;
import aug.laundry.enums.repair.RepairCategory;
import aug.laundry.service.*;
import aug.laundry.service.laundry.LaundryService;
import aug.laundry.service.login.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/laundry")
public class LaundryOrderController {

    private final MainService mainService;
    private final LaundryService laundryService;
    private final MemberService memberService;

    @PostMapping
    public String postLaundry(@SessionAttribute(name = SessionConstant.LOGIN_MEMBER, required = false) Long memberId,
                              @SessionAttribute(name = SessionConstant.ORDERS_CONFIRM, required = false) Long ordersDetailId,
                              String option,
                              FormatDate formatDate,
                              @RequestParam("service") List<String> service,
                              RedirectAttributes redirectAttributes){

        laundryService.checkOrder(ordersDetailId, option, service);

        // 빠른세탁 장바구니 추가
        if (option.equals("fast")){
            laundryService.insertQuickLaundry(ordersDetailId);
        }

        // 일반세탁 있으면
        if(service.contains("common")){
            laundryService.insertCommon(ordersDetailId);
        }

        redirectAttributes.addFlashAttribute("quick", option); // 빠른세탁이면 true 아니면 false
        redirectAttributes.addFlashAttribute("service", service); // 드라이클리닝, 생활빨, 수선 선택여부
        redirectAttributes.addFlashAttribute("dateTime", formatDate);
        redirectAttributes.addFlashAttribute("ordersDetailId", ordersDetailId);

        System.out.println("option = " + option);
        System.out.println("formatDate = " + formatDate);
        System.out.println("service = " + service);

        return "redirect:/laundry/order";
    }

    @GetMapping
    public String first(@SessionAttribute(name = SessionConstant.LOGIN_MEMBER, required = false) Long memberId, HttpSession session, Model model) {
        Optional<Member> member = laundryService.checkAddress(memberId);
        if (member.isEmpty()) return "redirect:/login";
        if (member.get().getMemberAddress() == null) {
            return "redirect:/members/" + memberId + "/address/update"; // 주소값이 없으면 주소 변경으로 리다이렉트
        }
        laundryService.check(memberId, session); // 이전에 존재하던 장바구니 테이블이 있으면 삭제
        OrdersDetail newOrdersDetail = laundryService.createOrdersDetail(memberId);// 장바구니 새로 생성

        // URL 경로 우회를 막기 위한 세션발급
        session.setAttribute(SessionConstant.ORDERS_CONFIRM, newOrdersDetail.getOrdersDetailId());
        log.info("Create Session (ORDERS_DETAIL_ID) = {}", newOrdersDetail.getOrdersDetailId());
        log.info("ORDERS_CONFIRM Session = {}", session.getAttribute(SessionConstant.ORDERS_CONFIRM));

        DateForm dateForm = new DateForm(); // 수거, 배송에 대한 정보
        model.addAttribute("dateTime", dateForm);
        return "project_order_1";
    }

}
