package org.zerock.natureRent.controller;


import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zerock.natureRent.entity.Blog;
import org.zerock.natureRent.entity.Member;
import org.zerock.natureRent.security.dto.MemberDTO;
import org.zerock.natureRent.service.BlogService;
import org.zerock.natureRent.service.ProductService;
//import org.zerock.natureRent.service.BlogService;

@Controller
@Log4j2
@RequestMapping("/blog/")
public class BlogController {

    private final BlogService blogService;

    private final ProductService productService;

    public BlogController(BlogService blogService, ProductService productService) {
        this.blogService = blogService;
        this.productService = productService;
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("blog-write")
    public String showBlogWriteForm() {
        return "blog/blog-write";
    }

//    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("blog-write")
    public String writeBlog(Blog blog, MemberDTO memberDTO) {
        // 블로그 작성자를 설정
        blog.setMember(Member.builder().email(memberDTO.getEmail()).build());
        blogService.saveBlog(blog, memberDTO);
        return "blog/blog-single-sidebar";
    }

    @GetMapping("blog/{id}")
    public String viewBlog(@PathVariable Long id, Model model) {
        model.addAttribute("blog", blogService.findBlogById(id));
        return "blog/blog-single";
    }

//    ////////////////////////////////////////////////////////////

    @GetMapping("blog-grid-sidebar")
    public String exBlogGridSidebar() {
        log.info("exBlogGridSidebar..........");
        return "blog/blog-grid-sidebar"; // 명시적으로 뷰 이름을 반환
    }

    @GetMapping("blog-single")
    public String exBlogSingle() {
        log.info("exBlogSingle..........");
        return "blog/blog-single"; // 명시적으로 뷰 이름을 반환
    }

    @GetMapping("blog-single-sidebar")
    public String exBlogSingleSidebar() {
        log.info("exBlogSingleSidebar..........");
        return "blog/blog-single-sidebar"; // 명시적으로 뷰 이름을 반환
    }


//    @GetMapping("/member")
//    public void exMember(){
//        log.info("exMember..........");
//    }

//    @PreAuthorize("hasRole('ADMIN')")
//    @GetMapping("admin")
//    public String exAdmin(){
//        log.info("exAdmin..........");
//        return "/main/admin"; // 명시적으로 뷰 이름을 반환
//
//    }
//
//    @PreAuthorize("hasRole('USER')")
//    @GetMapping("member")
//    public String exMember(@AuthenticationPrincipal MemberDTO clubAuthMember){
//
//        log.info("exMember..........");
//
//        log.info("-------------------------------");
//        log.info(clubAuthMember);
//        return "/main/member"; // 명시적으로 뷰 이름을 반환
//    }
//
//
//    //로그인한 사용자중에 user95@zerock.org만 접근가능하도록//
//    @PreAuthorize("#clubAuthMember != null && #clubAuthMember.username eq \"user95@zerock.org\"")
//    @GetMapping("/exOnly")
//    public String exMemberOnly(@AuthenticationPrincipal MemberDTO clubAuthMember){
//
//        log.info("exMemberOnly.............");
//        log.info(clubAuthMember);
//
//        return "/main/admin";
//    }

}
