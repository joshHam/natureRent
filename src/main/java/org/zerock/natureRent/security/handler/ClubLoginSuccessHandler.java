package org.zerock.natureRent.security.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.zerock.natureRent.security.dto.MemberDTO;

import jakarta.servlet.*;
import java.io.IOException;
import java.util.Collection;

@Log4j2
public class ClubLoginSuccessHandler implements AuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    private PasswordEncoder passwordEncoder;

    public ClubLoginSuccessHandler(PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        log.info("--------------------------------------");
        log.info("onAuthenticationSuccess");

        MemberDTO authMember = (MemberDTO)authentication.getPrincipal();

        boolean fromSocial = authMember.isFromSocial();

        log.info("Need Modify Member?" + fromSocial);

        boolean passwordResult = passwordEncoder.matches("1111", authMember.getPassword());

//        if(fromSocial && passwordResult) {
//            redirectStrategy.sendRedirect(request, response, "/member/modify?from=social");
////            redirectStrategy.sendRedirect(request, response, "/main/member/modify?from=social");
//        }
////        else {
////            redirectStrategy.sendRedirect(request, response, "/main/admin");
////        }

        // 소셜 로그인 시 비밀번호가 기본 값이면 비밀번호 변경 페이지로 리다이렉트
        if(fromSocial && passwordResult) {
            redirectStrategy.sendRedirect(request, response, "/member/modify?from=social");
            return;
        }

        // 사용자 권한에 따라 리다이렉트
        Collection<? extends GrantedAuthority> authorities = authMember.getAuthorities();
        for (GrantedAuthority authority : authorities) {
            log.info("Checking authority: " + authority.getAuthority());
//            log.info("Authority: " + authority.getAuthority()); // 추가된 디버깅 로그
            if (authority.getAuthority().equals("ROLE_ADMIN")) {
                log.info("Redirecting to: /main/admin");
                redirectStrategy.sendRedirect(request, response, "/main/admin");
                return;
            } else if (authority.getAuthority().equals("ROLE_USER")) {
                log.info("Redirecting to: /main/member");
                redirectStrategy.sendRedirect(request, response, "/main/member");
                return;
            } else if (authority.getAuthority().equals("ROLE_MANAGER")) {
                log.info("Redirecting to: /main/manager");
                redirectStrategy.sendRedirect(request, response, "/main/manager");
                return;
            }
        }
        // 권한이 없거나 해당되지 않으면 기본 페이지로 리다이렉트
        log.info("Redirecting to: /");
        redirectStrategy.sendRedirect(request, response, "/main/all");
    }

}
