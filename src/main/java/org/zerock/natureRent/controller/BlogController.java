package org.zerock.natureRent.controller;


import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.natureRent.dto.*;
import org.zerock.natureRent.entity.Blog;
import org.zerock.natureRent.entity.Member;
import org.zerock.natureRent.repository.MemberRepository;
import org.zerock.natureRent.security.dto.MemberDTO;
import org.zerock.natureRent.service.*;
//import org.zerock.natureRent.service.BlogService2;

import javax.xml.transform.Result;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
//import org.zerock.natureRent.service.BlogService;

@Controller
@Log4j2
@RequestMapping("/blog/")
public class BlogController {

    private final ReviewService reviewService;
    private final MemberRepository memberRepository;
    private final BlogService blogService;
    private final UploadController uploadController;
    private final CartService cartService;


    private final ProductService productService;

    public BlogController(ReviewService reviewService, MemberRepository memberRepository, BlogService blogService, UploadController uploadController, CartService cartService, ProductService productService) {
        this.reviewService = reviewService;
        this.memberRepository = memberRepository;
        this.blogService = blogService;
        this.uploadController = uploadController;
        this.cartService = cartService;
        this.productService = productService;
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("blog-write")
    public String showBlogWriteForm() {
        return "blog/blog-write";
    }

////    @PreAuthorize("hasRole('ROLE_USER')")
//    @PostMapping("blog-write")
//    public String writeBlog(Blog blog, @AuthenticationPrincipal MemberDTO memberDTO) {
//        if (memberDTO == null || memberDTO.getEmail() == null) {
//            throw new IllegalArgumentException("User is not authenticated or email is null");
//        }
//
//        // 이메일을 이용해 Member를 데이터베이스에서 찾기
//        Member member = memberRepository.findById(memberDTO.getEmail())
//                .orElseThrow(() -> new IllegalArgumentException("Member not found"));
//
////        Member member = Member.builder().email(memberDTO.getEmail()).build();
////        // Member 저장 (DB에 저장되지 않은 경우)
////        member = memberRepository.save(member);  // memberRepository는 JpaRepository 인터페이스를 구현해야 함
//
//        // 블로그 작성자를 설정
//        blog.setMember(member);
////        blog.setMember(Member.builder().email(memberDTO.getEmail()).build());
//        blogService.saveBlog(blog, memberDTO);
//        return "blog/blog-single-sidebar";
//    }


    @GetMapping("blog-write2")
    public String showBlogWriteForm2() {
        return "blog/blog-write2";
    }
    @PostMapping("blog-write2")
    public String writeBlog2(Blog blog,
                             @AuthenticationPrincipal MemberDTO memberDTO,
                             @RequestParam("uploadFiles") MultipartFile[] uploadFiles,
                             Model model,
                             @AuthenticationPrincipal MemberDTO authMember) {

        // 업로드된 파일 처리
        List<UploadResultDTO> uploadResultDTOList = uploadController.uploadFile(uploadFiles).getBody();

        if (uploadResultDTOList == null || uploadResultDTOList.isEmpty()) {
            model.addAttribute("error", "File upload failed");
            return "redirect:/blog/blog-write"; // 실패 시 다시 작성 페이지로 리다이렉트
        }

        // 업로드된 파일 정보를 ProductImageDTO 리스트로 변환
        List<ProductImageDTO> imageDTOList = new ArrayList<>();
        for (UploadResultDTO resultDTO : uploadResultDTOList) {
            ProductImageDTO imageDTO = ProductImageDTO.builder()
                    .imgName(resultDTO.getFileName())
                    .path(resultDTO.getFolderPath())
                    .uuid(resultDTO.getUuid())
                    .build();
            imageDTOList.add(imageDTO);
        }

        if (memberDTO == null || memberDTO.getEmail() == null) {
            throw new IllegalArgumentException("User is not authenticated or email is null");
        }

        // 이메일을 이용해 Member를 데이터베이스에서 찾기
        Member member = memberRepository.findById(memberDTO.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        // 블로그 작성자를 설정
        blog.setMember(member);
//        blog.setMember(Member.builder().email(memberDTO.getEmail()).build());
        blogService.saveBlog(blog, memberDTO, imageDTOList);


        Member member2 = authMember.getMember();
        List<CartDTO> cartList = cartService.getCartList(member2.getEmail()); // cartService를 사용해 CartDTO 리스트를 가져옴
        model.addAttribute("cartList", cartList);

        return "blog/blog-single-sidebar";
    }



//    @GetMapping("{id}"
@GetMapping("{id:[0-9]+}")
public String getBlogById(@PathVariable Long id, Model model, @AuthenticationPrincipal MemberDTO authMember) {
//        Blog blog = blogService.findBlogById(id);
    // ID에 해당하는 블로그와 관련된 이미지 데이터를 포함한 DTO를 가져온다.
    BlogDTO blogDTO = blogService.getBlogWithImages(id);
    // BlogDTO가 null인 경우 예외를 던진다.
    if (blogDTO== null) {
            throw new IllegalArgumentException("blogDTO not found");
        }
        // 로그로 확인
        log.info("Fetched Blog: " + blogDTO);
        log.info("blogDTOregDate: " + blogDTO.getRegDate());

    // Member email이 null인 경우 "Unknown Author"로 설정
    if (blogDTO.getMember_email() == null) {
        blogDTO.setMember_email("Unknown Author");
    }
        // 날짜를 문자열로 포맷팅
        String formattedRegDate = blogDTO.getRegDate() != null
                ? blogDTO.getRegDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                : "No date available";
    // 모델에 블로그 데이터와 포맷된 날짜를 추가
        model.addAttribute("blogDTO", blogDTO);// 여기서 blogDTO로 모델에 추가
        model.addAttribute("formattedRegDate", formattedRegDate);

    Member member = authMember.getMember();
    List<CartDTO> cartList = cartService.getCartList(member.getEmail()); // cartService를 사용해 CartDTO 리스트를 가져옴
    model.addAttribute("cartList", cartList);

    // 템플릿 반환
        return "blog/blog-single";
    }


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

//    @GetMapping("blog-grid-sidebar")
//    public String exBlogGridSidebar() {
//        log.info("exBlogGridSidebar..........");
//        return "blog/blog-grid-sidebar"; // 명시적으로 뷰 이름을 반환
//    }

    @GetMapping("blog-grid-sidebar")
    public String exBlogGridSidebar(PageRequestDTO pageRequestDTO, Model model,@AuthenticationPrincipal MemberDTO authMember) {
        log.info("exBlogGridSidebar..........");

//        // Blog 엔티티를 가져오는 서비스 메서드 호출 (예시로 가정)
//        List<Blog> blogList = blogService.getBlogListWithImages(); // 모든 Blog 엔티티를 가져오는 메서드

        // 페이지 사이즈를 9로 설정
        pageRequestDTO.setSize(6);

        // Pageable 객체를 PageRequestDTO에서 가져옴
        Pageable pageable = pageRequestDTO.getPageable(Sort.by("bno").descending());

        // Page 객체로 Blog 엔티티를 가져오는 서비스 메서드 호출
        Page<Blog> result = blogService.findAllBlogs(pageable); // pageable을 사용하여 페이징 처리



        // PageResultDTO로 변환
        PageResultDTO<BlogDTO, Blog> pageResultDTO = new PageResultDTO<>(result,
                blog -> BlogDTO.builder()
                        .bno(blog.getBno())
                        .title(blog.getTitle())
                        .detail(blog.getDetail())
                        .likes(blog.getLikes())
                        .views(blog.getViews())
                        .tags(blog.getTags())
                        .member_email(blog.getMember().getEmail())
                        .build()
        );

        log.info("PageResultDTO: " + pageResultDTO);
        pageResultDTO.getDtoList().forEach(dto -> log.info("BlogDTO: " + dto));

//        PageResultDTO<BlogDTO, Blog> result = blogService.getList(pageRequestDTO);

        model.addAttribute("result", pageResultDTO);

        Member member = authMember.getMember();
        List<CartDTO> cartList = cartService.getCartList(member.getEmail()); // cartService를 사용해 CartDTO 리스트를 가져옴
        model.addAttribute("cartList", cartList);

        return "blog/blog-grid-sidebar"; // 명시적으로 뷰 이름을 반환
    }


//    @GetMapping("blog-single")
//    public String exBlogSingle() {
//        log.info("exBlogSingle..........");
//        return "blog/blog-single"; // 명시적으로 뷰 이름을 반환
//    }

    @GetMapping("/blog/blog-single")
    public String getBlogSingle(@RequestParam("bno") Long bno, @RequestParam("page") int page, Model model,
                                @AuthenticationPrincipal MemberDTO authMember) {
        // 서비스 메서드를 호출하여 블로그 데이터를 가져옴
        Blog blog = blogService.findBlogById(bno);

        // 블로그 데이터를 모델에 추가
        model.addAttribute("blog", blog);
        model.addAttribute("page", page);

        // 등록일 포맷 설정
        if (blog != null) {
            String formattedRegDate = blog.getRegDate() != null ? blog.getRegDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : "No date available";
            model.addAttribute("formattedRegDate", formattedRegDate);
        }

        Member member = authMember.getMember();
        List<CartDTO> cartList = cartService.getCartList(member.getEmail()); // cartService를 사용해 CartDTO 리스트를 가져옴
        model.addAttribute("cartList", cartList);

        return "blog/blog-single";
    }


    @GetMapping("blog-single-sidebar")
    public String exBlogSingleSidebar(Model model,
                                      @AuthenticationPrincipal MemberDTO authMember) {
        log.info("exBlogSingleSidebar..........");

        Member member = authMember.getMember();
        List<CartDTO> cartList = cartService.getCartList(member.getEmail()); // cartService를 사용해 CartDTO 리스트를 가져옴
        model.addAttribute("cartList", cartList);

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
