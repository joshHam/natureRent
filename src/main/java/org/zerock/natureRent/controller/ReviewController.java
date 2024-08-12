package org.zerock.natureRent.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.natureRent.dto.ReviewDTO;
import org.zerock.natureRent.service.ReviewService;

import java.util.List;

//@RestController
@Controller
@RequestMapping("/reviews")
@Log4j2
@RequiredArgsConstructor
    public class ReviewController {

    private final ReviewService reviewService;

//    public ReviewController(ReviewService reviewService) {
//        this.reviewService = reviewService;
//    }

    @GetMapping("/{mno}/all")
    public ResponseEntity<List<ReviewDTO>> getList(@PathVariable("mno") Long mno){
        log.info("--------------list---------------");
        log.info("MNO: " + mno);

        List<ReviewDTO> reviewDTOList = reviewService.getListOfProduct(mno);

        return new ResponseEntity<>(reviewDTOList, HttpStatus.OK);
    }

    @PostMapping("/{mno}")
    public ResponseEntity<Long> addReview(@RequestBody ReviewDTO productReviewDTO){
        log.info("--------------add ProductReview---------------");
        log.info("reviewDTO: " + productReviewDTO);

        Long reviewnum = reviewService.register(productReviewDTO);

        return new ResponseEntity<>( reviewnum, HttpStatus.OK);
    }

    @PutMapping("/{mno}/{reviewnum}")
    public ResponseEntity<Long> modifyReview(@PathVariable Long reviewnum,
                                             @RequestBody ReviewDTO productReviewDTO){
        log.info("---------------modify ProductReview--------------" + reviewnum);
        log.info("reviewDTO: " + productReviewDTO);

        reviewService.modify(productReviewDTO);

        return new ResponseEntity<>( reviewnum, HttpStatus.OK);
    }

    @DeleteMapping("/{mno}/{reviewnum}")
    public ResponseEntity<Long> removeReview( @PathVariable Long reviewnum){
        log.info("---------------modify removeReview--------------");
        log.info("reviewnum: " + reviewnum);

        reviewService.remove(reviewnum);

        return new ResponseEntity<>( reviewnum, HttpStatus.OK);
    }


    @GetMapping("/blog/{blogId}")
    public ResponseEntity<List<ReviewDTO>> getReviewsByBlogId(@PathVariable Long blogId) {
        List<ReviewDTO> reviews = reviewService.getListOfBlog(blogId);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ReviewDTO>> getReviewsByProductId(@PathVariable Long productId) {
        List<ReviewDTO> reviews = reviewService.getListOfProduct(productId);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }



//    @PostMapping("/add")
//    public String addReview(
//            @RequestParam("name") String name,
//            @RequestParam("email") String email,
//            @RequestParam("text") String text,
//            @RequestParam("blogId") Long blogId,
//            RedirectAttributes redirectAttributes) {
//        ReviewDTO reviewDTO = new ReviewDTO();
//        reviewDTO.setName(name);
//        reviewDTO.setMember_email(email);
//        reviewDTO.setText(text);
//        reviewDTO.setBno(blogId); // 블로그 ID 설정
//        reviewService.register(reviewDTO);
//        // 리뷰 등록 후 생성된 reviewnum을 가져옵니다.
////        Long reviewnum = reviewService.register(reviewDTO);
//        redirectAttributes.addFlashAttribute("message", "Review added successfully!");
//        return "redirect:http://localhost:8080/blog/" + blogId;
//    }


}
