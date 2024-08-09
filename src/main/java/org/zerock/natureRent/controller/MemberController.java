package org.zerock.natureRent.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.zerock.natureRent.security.dto.MemberDTO;
import org.zerock.natureRent.security.service.ClubUserDetailsService;
import org.zerock.natureRent.security.service.ClubUserDetailsService;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final ClubUserDetailsService memberService;

    @GetMapping("/register")
    public String registerForm() {
        return "register";
    }

    @PostMapping("/register")
    public String register(MemberDTO memberDTO) {
        memberService.register(memberDTO);
        return "redirect:/login"; // 회원가입 후 로그인 페이지로 리다이렉트
    }
}
