package org.zerock.natureRent.controller;

import lombok.RequiredArgsConstructor;
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

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final ClubUserDetailsService memberService;
    private final CartService cartService;
    @GetMapping("/register")
    public String registerForm() {
        return "register";
    }

    @PostMapping("/register")
    public String register(MemberDTO memberDTO, @AuthenticationPrincipal MemberDTO authMember, Model model) {
        memberService.register(memberDTO);
        Member member = authMember.getMember();
        List<CartDTO> cartList = cartService.getCartList(member.getEmail()); // cartService를 사용해 CartDTO 리스트를 가져옴
        model.addAttribute("cartList", cartList);
        return "redirect:/login"; // 회원가입 후 로그인 페이지로 리다이렉트
    }
}
