package org.zerock.natureRent.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.zerock.natureRent.dto.CartDTO;
import org.zerock.natureRent.entity.Member;
import org.zerock.natureRent.security.dto.MemberDTO;
import org.zerock.natureRent.security.service.ClubUserDetailsService;
import org.zerock.natureRent.security.service.ClubUserDetailsService;
import org.zerock.natureRent.service.CartService;

import java.util.List;


@Log4j2
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final ClubUserDetailsService memberService;
    private final CartService cartService;

//    @GetMapping("/register")
//    public String register() {
//        return "register";
//    }
@GetMapping("/register")
public String register(@AuthenticationPrincipal MemberDTO authMember, Model model) {
    if (authMember != null) {
        Member member = authMember.getMember();
        List<CartDTO> cartList = cartService.getCartList(member.getEmail());
        model.addAttribute("cartList", cartList);
    }else {
        log.warn("User is not authenticated.");
        // 인증되지 않은 경우에 대해 별도로 처리할 수 있습니다.
    }
    return "register";
}



//    @GetMapping("/register")
    @PostMapping("/register")
    public String register(MemberDTO memberDTO,
                           @AuthenticationPrincipal MemberDTO authMember,
                           Model model) {
        memberService.register(memberDTO);
        if (authMember != null) {
            Member member = authMember.getMember();
            List<CartDTO> cartList = cartService.getCartList(member.getEmail());
            model.addAttribute("cartList", cartList);
        }else {
            log.warn("User is not authenticated.");
            // 인증되지 않은 경우에 대해 별도로 처리할 수 있습니다.
        }
        return "redirect:/login"; // 회원가입 후 로그인 페이지로 리다이렉트
    }
}
