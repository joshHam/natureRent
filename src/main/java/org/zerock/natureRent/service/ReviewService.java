package org.zerock.natureRent.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.zerock.natureRent.dto.ReviewDTO;
import org.zerock.natureRent.entity.*;
import org.zerock.natureRent.repository.ReviewRepository;

import java.util.List;

public interface ReviewService {
    // 블로그에 대한 모든 리뷰를 가져온다.
    List<ReviewDTO> getListOfBlog(Long bno);

    // 블로그 리뷰를 추가
    Long registerBlogReview(ReviewDTO blogReviewDTO);

    // 블로그 리뷰 수정
    void modifyBlogReview(ReviewDTO blogReviewDTO);

    // 블로그 리뷰 삭제
    void removeBlogReview(Long reviewnum);
/////////////////////////////////////////////////////






    ////////////////////////////////////////////
    //상품의 모든 상품리뷰를 가져온다.
    List<ReviewDTO> getListOfProduct(Long mno);

    //상품 리뷰를 추가
    Long register(ReviewDTO productReviewDTO);

    //특정한 상품리뷰 수정
    void modify(ReviewDTO productReviewDTO);

    //상품 리뷰 삭제
    void remove(Long reviewnum);

//    default Review dtoToEntity(ReviewDTO productReviewDTO){
//
//        Review productReview = Review.builder()
//                .reviewnum(productReviewDTO.getReviewnum())
//                .product(Product.builder().mno(productReviewDTO.getMno()).build())
////                .member(Member.builder().email(productReviewDTO.getMid()).build())
//                .member(Member.builder().email(productReviewDTO.getMember_email()).build())
//
//                .grade(productReviewDTO.getGrade())
//                .text(productReviewDTO.getText())
//                .build();
//
//        return productReview;
//    }
//
//    default ReviewDTO entityToDto(Review productReview){
//
//        ReviewDTO productReviewDTO = ReviewDTO.builder()
//                .reviewnum(productReview.getReviewnum())
//                .mno(productReview.getProduct().getMno())
////                .mid(productReview.getMember().getMid())
////                .mid(productReview.getMember().getEmail())
////                .nickname(productReview.getMember().getNickname())
//                .name(productReview.getMember().getName())
//                .member_email(productReview.getMember().getEmail())
//                .grade(productReview.getGrade())
//                .text(productReview.getText())
//                .regDate(productReview.getRegDate())
//                .modDate(productReview.getModDate())
//                .build();
//
//        return productReviewDTO;
//    }

    default Review dtoToEntity(ReviewDTO reviewDTO) {
        Review review = Review.builder()
                .reviewnum(reviewDTO.getReviewnum())
                .product(reviewDTO.getMno() != null ? Product.builder().mno(reviewDTO.getMno()).build() : null)
                .blog(reviewDTO.getBno() != null ? Blog.builder().bno(reviewDTO.getBno()).build() : null)
                .member(Member.builder().email(reviewDTO.getMember_email()).build())
                .grade(reviewDTO.getGrade())
                .text(reviewDTO.getText())
                .build();
        return review;
    }

    default ReviewDTO entityToDto(Review review) {
        ReviewDTO reviewDTO = ReviewDTO.builder()
                .reviewnum(review.getReviewnum())
                .mno(review.getProduct() != null ? review.getProduct().getMno() : null)
                .bno(review.getBlog() != null ? review.getBlog().getBno() : null)
                .name(review.getMember().getName())
                .member_email(review.getMember().getEmail())
                .grade(review.getGrade())
                .text(review.getText())
                .regDate(review.getRegDate())
                .modDate(review.getModDate())
                .build();
        return reviewDTO;
    }



}
