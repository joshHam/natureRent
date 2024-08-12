package org.zerock.natureRent.controller;


import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.natureRent.dto.ReviewDTO;
import org.zerock.natureRent.entity.Blog;
import org.zerock.natureRent.entity.Member;
import org.zerock.natureRent.repository.MemberRepository;
import org.zerock.natureRent.security.dto.MemberDTO;
import org.zerock.natureRent.service.BlogService;
import org.zerock.natureRent.service.ProductService;
import org.zerock.natureRent.service.ReviewService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
//import org.zerock.natureRent.service.BlogService;

@Controller
@Log4j2
@RequestMapping("/blog/")
public class BlogController {

    private final ReviewService reviewService;
    private final MemberRepository memberRepository;
    private final BlogService blogService;

    private final ProductService productService;

    public BlogController(ReviewService reviewService, MemberRepository memberRepository, BlogService blogService, ProductService productService) {
        this.reviewService = reviewService;
        this.memberRepository = memberRepository;
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
    public String writeBlog(Blog blog, @AuthenticationPrincipal MemberDTO memberDTO) {
        if (memberDTO == null || memberDTO.getEmail() == null) {
            throw new IllegalArgumentException("User is not authenticated or email is null");
        }

        // 이메일을 이용해 Member를 데이터베이스에서 찾기
        Member member = memberRepository.findById(memberDTO.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

//        Member member = Member.builder().email(memberDTO.getEmail()).build();
//        // Member 저장 (DB에 저장되지 않은 경우)
//        member = memberRepository.save(member);  // memberRepository는 JpaRepository 인터페이스를 구현해야 함

        // 블로그 작성자를 설정
        blog.setMember(member);
//        blog.setMember(Member.builder().email(memberDTO.getEmail()).build());
        blogService.saveBlog(blog, memberDTO);
        return "blog/blog-single-sidebar";
    }

////    @GetMapping("blog/{id}")
//    public String viewBlog(@PathVariable Long id, Model model) {
////        Blog blog = blogService.findBlogById(id);
////        if (blog == null) {
////            throw new IllegalArgumentException("Blog not found");
////        }
////
////        model.addAttribute("blog", blog);
//////        model.addAttribute("blog", blogService.findBlogById(id));
//
//
//
//        Blog blog = new Blog();
//        blog.setTitle("Sample Title");
//        blog.setDetail("This is a sample blog detail.");
//        blog.setTags("sample, test");
//
//
//        Member member = new Member();
//        member.setName("John Doe");
//        blog.setMember(member);
//
//        model.addAttribute("blog", blog);
//        return "blog/blog-single";
//    }




    @GetMapping("{id}")
    public String getBlogById(@PathVariable Long id, Model model) {
        Blog blog = blogService.findBlogById(id);
        if (blog == null) {
            throw new IllegalArgumentException("Blog not found");
        }
        // 로그로 확인
        log.info("Fetched Blog: " + blog);
        log.info("Blog regDate: " + blog.getRegDate());
        if (blog.getMember() == null) {
            blog.setMember(new Member());
            blog.getMember().setName("Unknown Author");
        }
        // 날짜를 문자열로 포맷팅
        String formattedRegDate = blog.getRegDate() != null
                ? blog.getRegDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                : "No date available";
        model.addAttribute("blog", blog);
        model.addAttribute("formattedRegDate", formattedRegDate);
        return "blog/blog-single";
    }

//    @GetMapping("{blogId}")
////    @ResponseBody
//    public ResponseEntity<List<ReviewDTO>> getReviewsByBlogId(@PathVariable Long blogId) {
//        List<ReviewDTO> reviews = reviewService.getListOfBlog(blogId);
//        return new ResponseEntity<>(reviews, HttpStatus.OK);
//    }

    // 블로그에 대한 리뷰 가져오기
    @GetMapping("{blogId}/reviews")
    public ResponseEntity<List<ReviewDTO>> getReviewsByBlogId(@PathVariable Long blogId) {
        List<ReviewDTO> reviews = reviewService.getListOfBlog(blogId);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    // 블로그에 리뷰 추가하기
    @PostMapping("reviews/add")
    public String addReview(
            @RequestParam("blogId") Long blogId,
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("text") String text,
            RedirectAttributes redirectAttributes) {
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setName(name);
        reviewDTO.setMember_email(email);
        reviewDTO.setText(text);
        reviewDTO.setBno(blogId); // 블로그 ID 설정
        reviewService.register(reviewDTO);
        redirectAttributes.addFlashAttribute("message", "Review added successfully!");
        return "redirect:/blog/" + blogId;
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


    @GetMapping("mock")
    public String viewMockBlog(Model model) {
        Blog blog = new Blog();
        blog.setTitle("Sample Title");
        blog.setDetail("This is a sample blog detail.");
        blog.setTags("sample, test");

        Member member = new Member();
        member.setName("John Doe");
        blog.setMember(member);

        model.addAttribute("blog", blog);
        return "blog/blog-single";
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
