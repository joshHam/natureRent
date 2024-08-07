package org.zerock.natureRent.service;

import org.zerock.natureRent.dto.ReviewDTO;
import org.zerock.natureRent.entity.Member;
import org.zerock.natureRent.entity.MemberOriginal;
import org.zerock.natureRent.entity.Product;
import org.zerock.natureRent.entity.Review;

import java.util.List;

public interface ReviewService {

    //영화의 모든 영화리뷰를 가져온다.
    List<ReviewDTO> getListOfProduct(Long mno);

    //영화 리뷰를 추가
    Long register(ReviewDTO productReviewDTO);

    //특정한 영화리뷰 수정
    void modify(ReviewDTO productReviewDTO);

    //영화 리뷰 삭제
    void remove(Long reviewnum);

    default Review dtoToEntity(ReviewDTO productReviewDTO){

        Review productReview = Review.builder()
                .reviewnum(productReviewDTO.getReviewnum())
                .product(Product.builder().mno(productReviewDTO.getMno()).build())
//                .member(Member.builder().email(productReviewDTO.getMid()).build())
                .member(Member.builder().email(productReviewDTO.getMember_email()).build())

                .grade(productReviewDTO.getGrade())
                .text(productReviewDTO.getText())
                .build();

        return productReview;
    }

    default ReviewDTO entityToDto(Review productReview){

        ReviewDTO productReviewDTO = ReviewDTO.builder()
                .reviewnum(productReview.getReviewnum())
                .mno(productReview.getProduct().getMno())
//                .mid(productReview.getMember().getMid())
//                .mid(productReview.getMember().getEmail())
//                .nickname(productReview.getMember().getNickname())
                .name(productReview.getMember().getName())
                .member_email(productReview.getMember().getEmail())
                .grade(productReview.getGrade())
                .text(productReview.getText())
                .regDate(productReview.getRegDate())
                .modDate(productReview.getModDate())
                .build();

        return productReviewDTO;
    }
}
