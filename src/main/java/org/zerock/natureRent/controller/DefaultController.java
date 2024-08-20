package org.zerock.natureRent.controller;


import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.zerock.natureRent.dto.CartDTO;
import org.zerock.natureRent.entity.Member;
import org.zerock.natureRent.security.dto.MemberDTO;
import org.zerock.natureRent.service.CartService;

import java.util.List;

@Controller
@Log4j2
public class DefaultController {
    private final CartService cartService;

    public DefaultController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/404")
    public String Error() {
        return "404";
    }

    @GetMapping("/contact")
    public String Contact() {
        return "contact";
    }

    @GetMapping("/index")
    public String Index() {
        return "index";
    }

    @GetMapping("/loginT")
    public String LoginT() {
        return "loginT";
    }

    @GetMapping("/mail-success")
    public String MailSuccess(Model model,@AuthenticationPrincipal MemberDTO authMember) {
        Member member = authMember.getMember();
        List<CartDTO> cartList = cartService.getCartList(member.getEmail()); // cartService를 사용해 CartDTO 리스트를 가져옴
        model.addAttribute("cartList", cartList);
        return "mail-success";
    }

/*    @GetMapping("/register")
    public String Register() {
        return "register";
    }*/

/*
    @GetMapping("/about-us")
    public String AboutUs() {
        return "about-us";
    }

    @GetMapping("/about-us")
    public String AboutUs() {
        return "about-us";
    }
*/





}















