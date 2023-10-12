package aug.laundry.controller.laundry;

import aug.laundry.commom.SessionConstant;
import aug.laundry.domain.Repair;
import aug.laundry.domain.RepairImage;
import aug.laundry.dto.RepairFormData;
import aug.laundry.enums.category.CategoryPriceCalculator;
import aug.laundry.enums.category.Pass;
import aug.laundry.enums.fileUpload.FileUploadType;
import aug.laundry.enums.repair.RepairCategory;
import aug.laundry.service.FileUploadService;
import aug.laundry.service.laundry.RepairService;
import aug.laundry.service.login.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/laundry")
public class RepairController {

    private final RepairService repairService;
    private final MemberService memberService;
    private final FileUploadService fileUploadService;


    @GetMapping("/repair")
    public String repair(@SessionAttribute(name = SessionConstant.LOGIN_MEMBER, required = false) Long memberId,
                         @SessionAttribute(name = SessionConstant.ORDERS_CONFIRM, required = false) Long orderDetailId,
                         Model model) {
        if (memberService.isPass(memberId).getCheck() == Pass.PASS){
            model.addAttribute("percent", CategoryPriceCalculator.PASS.percent());
        }

        List<Repair> reload = repairService.reloadRepair(orderDetailId);
        if (reload != null && !reload.isEmpty()) {
            Map<Long, List<RepairImage>> uploadImage = repairService.getRepairImage(reload);
            model.addAttribute("uploadImage", uploadImage);
        }


        model.addAttribute("reload", reload);
        model.addAttribute("repairCategory", RepairCategory.values());


        repairService.removeRepair(reload); // reload하고 기존에 있던 목록 전부 제거
        return "project_order_2_2";
    }

    @GetMapping("/repair/check")
    public @ResponseBody String repairCheck(@SessionAttribute(name = SessionConstant.ORDERS_CONFIRM, required = false) Long ordersDetailId) {
        log.info("Repair check [ ordersDetailId = {} ]", ordersDetailId);
        List<Repair> repairList = repairService.reloadRepair(ordersDetailId);

        repairService.removeRepair(repairList);

        return "";

    }

    @PostMapping(value = "/repair/order")
    public @ResponseBody Map<String, Boolean> repairOrder(@SessionAttribute(name = SessionConstant.LOGIN_MEMBER, required = false) Long memberId,
                                                          @SessionAttribute(name = SessionConstant.ORDERS_CONFIRM, required = false) Long ordersDetailId,
                                                          @RequestPart("repairData") Map<String, RepairFormData> repairData,
                                                          @RequestParam(name = "files", required = false) List<MultipartFile> files) {
        Map<String, Boolean> resultMap = new ConcurrentHashMap<>();
        System.out.println("files = " + files);

        // memberId, ordersDetailId == null 일경우 false 반환
        boolean status = repairService.valid(memberId, ordersDetailId);
        if (!status){
            resultMap.put("status", false);
            return resultMap;
        }
        Repair saveRepair = repairService.insertRepair(ordersDetailId, repairData, files);

        if (saveRepair != null) {
            int saveFile = fileUploadService.saveFile(files, saveRepair.getRepairId(), FileUploadType.REPAIR);
            log.info("saveFile = {}", saveFile);
        }
        log.info("repairData = {}", repairData);
        log.info("files = {}", files);

        return resultMap;
    }
}
