package org.zerock.natureRent.controller;


import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zerock.natureRent.security.dto.ClubAuthMemberDTO;
import org.zerock.natureRent.dto.ProductDTO;
import org.zerock.natureRent.dto.PageRequestDTO;
import org.zerock.natureRent.dto.PageResultDTO;
import org.zerock.natureRent.service.ProductService;

@Controller
@Log4j2
@RequestMapping("/sample/")
public class SampleController {

    private final ProductService productService;

    public SampleController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/all")
    public String exAll(Model model, PageRequestDTO pageRequestDTO){
        log.info("exAll..........");
        PageResultDTO<ProductDTO, Object[]> result = productService.getList(pageRequestDTO);
        model.addAttribute("result", result);

        return "sample/all";
    }

    /*@GetMapping("/product-details")
    public String exProductDetails(Model model, PageRequestDTO pageRequestDTO){
        log.info("exProductDetails..........");
        PageResultDTO<ProductDTO, Object[]> result = productService.getList(pageRequestDTO);
        model.addAttribute("result", result);

        return "sample/product-details";
    }*/

//    @GetMapping({"/read", "/modify"})
@GetMapping("/product-details")
    public void read(long mno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model ){

        log.info("mno: " + mno);

        ProductDTO productDTO = productService.getProduct(mno);

        model.addAttribute("dto", productDTO);

//    log.info("exProductDetails..........");
//    if (mno == null) {
//        log.warn("mno parameter is missing");
//        return "redirect:/error"; // 매개변수가 없을 때 예외 처리
//    }
//
//    ProductDTO productDTO = productService.getProduct(mno);
//    if (productDTO == null) {
//        log.warn("Product not found with mno: " + mno);
//        return "redirect:/error"; // 예외 처리
//    }
//    log.info("ProductDTO: " + productDTO);
//    model.addAttribute("dto", productDTO);
//    return "sample/product-details";

    }

    @GetMapping("faq")
    public String exFaq(){
        log.info("exFaq..........");
        return "/sample/faq"; // 명시적으로 뷰 이름을 반환

    }





//    @GetMapping("/member")
//    public void exMember(){
//        log.info("exMember..........");
//    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("admin")
    public String exAdmin(){
        log.info("exAdmin..........");
        return "/sample/admin"; // 명시적으로 뷰 이름을 반환

    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("member")
    public String exMember(@AuthenticationPrincipal ClubAuthMemberDTO clubAuthMember){

        log.info("exMember..........");

        log.info("-------------------------------");
        log.info(clubAuthMember);
        return "/sample/member"; // 명시적으로 뷰 이름을 반환
    }


    //로그인한 사용자중에 user95@zerock.org만 접근가능하도록//
    @PreAuthorize("#clubAuthMember != null && #clubAuthMember.username eq \"user95@zerock.org\"")
    @GetMapping("/exOnly")
    public String exMemberOnly(@AuthenticationPrincipal ClubAuthMemberDTO clubAuthMember){

        log.info("exMemberOnly.............");
        log.info(clubAuthMember);

        return "/sample/admin";
    }

}
