/*
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

import java.util.List;

@Controller
@Log4j2
@RequestMapping("/main/")
public class SampleController2 {

    private final ProductService productService;

    public SampleController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/all")
    public String exAll(Model model, PageRequestDTO pageRequestDTO){
        log.info("exAll..........");

        PageResultDTO<ProductDTO, Object[]> result = productService.getList(pageRequestDTO);
        model.addAttribute("result", result);

        return "main/all";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public void exAdmin(){
        log.info("exAdmin..........");
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/member")
    public void exMember(@AuthenticationPrincipal ClubAuthMemberDTO clubAuthMember){

        log.info("exMember..........");

        log.info("-------------------------------");
        log.info(clubAuthMember);

    }

    @PreAuthorize("#clubAuthMember != null && #clubAuthMember.username eq \"user95@zerock.org\"")
    @GetMapping("/exOnly")
    public String exMemberOnly(@AuthenticationPrincipal ClubAuthMemberDTO clubAuthMember){

        log.info("exMemberOnly.............");
        log.info(clubAuthMember);

        return "/main/admin";
    }

}
*/
