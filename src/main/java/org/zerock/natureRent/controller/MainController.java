package org.zerock.natureRent.controller;


import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zerock.natureRent.security.dto.ClubAuthMemberDTO;
import org.zerock.natureRent.dto.ProductDTO;
import org.zerock.natureRent.dto.PageRequestDTO;
import org.zerock.natureRent.dto.PageResultDTO;
import org.zerock.natureRent.service.ProductService;

@Controller
@Log4j2
@RequestMapping("/main/")
public class MainController {

    private final ProductService productService;

    public MainController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/all")
    public String exAll(Model model, PageRequestDTO pageRequestDTO){
        log.info("exAll..........");
        PageResultDTO<ProductDTO, Object[]> result = productService.getList(pageRequestDTO);
        model.addAttribute("result", result);

        return "main/all";
    }

    /*@GetMapping("/product-details")
    public String exProductDetails(Model model, PageRequestDTO pageRequestDTO){
        log.info("exProductDetails..........");
        PageResultDTO<ProductDTO, Object[]> result = productService.getList(pageRequestDTO);
        model.addAttribute("result", result);

        return "main/product-details";
    }*/

//    @GetMapping({"/read", "/modify"})




    @GetMapping("cart")
    public String exCart(){
        log.info("exCart..........");
        return "/main/cart"; // 명시적으로 뷰 이름을 반환

    }

    @GetMapping("about-us")
    public String exAboutUs(){
        log.info("exAboutUs..........");
        return "/main/about-us"; // 명시적으로 뷰 이름을 반환

    }

    @GetMapping("checkout")
    public String exCheckOut(){
        log.info("exCheckOut..........");
        return "/main/checkout"; // 명시적으로 뷰 이름을 반환

    }

    @GetMapping("faq")
    public String exFaq(){
        log.info("exFaq..........");
        return "/main/faq"; // 명시적으로 뷰 이름을 반환

    }


//    @GetMapping("/member")
//    public void exMember(){
//        log.info("exMember..........");
//    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("admin")
    public String exAdmin(){
        log.info("exAdmin..........");
        return "/main/admin"; // 명시적으로 뷰 이름을 반환

    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("member")
    public String exMember(@AuthenticationPrincipal ClubAuthMemberDTO clubAuthMember){

        log.info("exMember..........");

        log.info("-------------------------------");
        log.info(clubAuthMember);
        return "/main/member"; // 명시적으로 뷰 이름을 반환
    }


    //로그인한 사용자중에 user95@zerock.org만 접근가능하도록//
    @PreAuthorize("#clubAuthMember != null && #clubAuthMember.username eq \"user95@zerock.org\"")
    @GetMapping("/exOnly")
    public String exMemberOnly(@AuthenticationPrincipal ClubAuthMemberDTO clubAuthMember){

        log.info("exMemberOnly.............");
        log.info(clubAuthMember);

        return "/main/admin";
    }

}
