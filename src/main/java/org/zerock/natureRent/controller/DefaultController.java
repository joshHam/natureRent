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
public class DefaultController {

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
    public String MailSuccess() {
        return "mail-success";
    }

    @GetMapping("/register")
    public String Register() {
        return "register";
    }

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















