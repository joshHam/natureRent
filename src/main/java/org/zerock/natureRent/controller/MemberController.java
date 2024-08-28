package org.zerock.natureRent.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.zerock.natureRent.dto.CartDTO;
import org.zerock.natureRent.entity.Member;
import org.zerock.natureRent.security.dto.MemberDTO;
import org.zerock.natureRent.security.service.ClubUserDetailsService;
import org.zerock.natureRent.security.service.ClubUserDetailsService;
import org.zerock.natureRent.service.CartService;

import java.util.List;
import java.util.Map;


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


    @PostMapping("/api/check-email")
    public ResponseEntity<Boolean> checkEmail(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        boolean exists = memberService.isEmailExists(email); // 이메일 존재 여부 체크 메서드
        return ResponseEntity.ok(exists);
    }

//    // 이메일 중복 검사 API
//@PostMapping("/api/check-email")
//public ResponseEntity<Boolean> checkEmail(@RequestBody String email) {
//    boolean exists = memberService.isEmailExists(email); // 이메일 존재 여부 체크 메서드
//    return ResponseEntity.ok(exists);
//}


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
        try {
            memberService.register(memberDTO);
            if (authMember != null) {
                Member member = authMember.getMember();
                List<CartDTO> cartList = cartService.getCartList(member.getEmail());
                model.addAttribute("cartList", cartList);
            }else {
                log.warn("User is not authenticated.");
                // 인증되지 않은 경우에 대해 별도로 처리할 수 있습니다.
            }
            return "redirect:/login"; // 회원가입 성공 시 리다이렉트
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            if (authMember != null) {
                Member member = authMember.getMember();
                List<CartDTO> cartList = cartService.getCartList(member.getEmail());
                model.addAttribute("cartList", cartList);
            }else {
                log.warn("User is not authenticated.");
                // 인증되지 않은 경우에 대해 별도로 처리할 수 있습니다.
            }
            return "register"; // 회원가입 폼으로 다시 이동하며 에러 메시지 표시
        }


//        return "redirect:/login"; // 회원가입 후 로그인 페이지로 리다이렉트
    }
}
