package org.zerock.natureRent.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.zerock.natureRent.dto.ReviewDTO;
import org.zerock.natureRent.entity.Blog;
import org.zerock.natureRent.entity.Product;
import org.zerock.natureRent.entity.Review;
import org.zerock.natureRent.repository.ReviewRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;


    @Override
    public List<ReviewDTO> getListOfProduct(Long mno){

        Product product = Product.builder().mno(mno).build();

        List<Review> result = reviewRepository.findByProduct(product);

//        return result.stream().map(productReview -> entityToDto(productReview)).collect(Collectors.toList());
        return result.stream().map(this::entityToDto).collect(Collectors.toList());

    }

    @Override
    public Long register(ReviewDTO productReviewDTO) {

        Review productReview = dtoToEntity(productReviewDTO);

        reviewRepository.save(productReview);

        return productReview.getReviewnum();
    }

//    @Override
//    public void modify(ReviewDTO productReviewDTO) {
//
//        Optional<Review> result =
//                reviewRepository.findById(productReviewDTO.getReviewnum());
//
//        if(result.isPresent()){
//
//            Review productReview = result.get();
//            productReview.changeGrade(productReviewDTO.getGrade());
//            productReview.changeText(productReviewDTO.getText());
//
//            reviewRepository.save(productReview);
//        }
//
//    }

    @Override
    public void modify(ReviewDTO productReviewDTO) {
        Optional<Review> result = reviewRepository.findById(productReviewDTO.getReviewnum());
        result.ifPresent(productReview -> {
            productReview.changeGrade(productReviewDTO.getGrade());
            productReview.changeText(productReviewDTO.getText());
            reviewRepository.save(productReview);
        });
    }


    @Override
    public void remove(Long reviewnum) {

        reviewRepository.deleteById(reviewnum);

    }


    ///////////////////////////////////////////

    @Override
    public List<ReviewDTO> getListOfBlog(Long bno) {
        Blog blog = Blog.builder().bno(bno).build();
        List<Review> result = reviewRepository.findByBlogBno(bno);
        return result.stream().map(this::entityToDto).collect(Collectors.toList());
        //        return result.stream()
////                .map(this::entityToDto)
//                .map(review ->entityToDto(review)) //엔티티를 DTO로 변환
//                .collect(Collectors.toList());
//    }
//
//    // 엔티티를 DTO로 변환하는 메소드 (예시)
//    public ReviewDTO entityToDto(Review review) {
//        return ReviewDTO.builder()
//                .reviewnum(review.getReviewnum())
//                .bno(review.getBlog().getBno())
//                .name(review.getMember().getName())
//                .text(review.getText())
//                .regDate(review.getRegDate())
//                .build();
    }

    @Override
    public Long registerBlogReview(ReviewDTO blogReviewDTO) {
        Review blogReview = dtoToEntity(blogReviewDTO);
        reviewRepository.save(blogReview);
        return blogReview.getReviewnum();
    }

    @Override
    public void modifyBlogReview(ReviewDTO blogReviewDTO) {
        Optional<Review> result = reviewRepository.findById(blogReviewDTO.getReviewnum());
        result.ifPresent(blogReview -> {
            blogReview.changeGrade(blogReviewDTO.getGrade());
            blogReview.changeText(blogReviewDTO.getText());
            reviewRepository.save(blogReview);
        });

        //        if (result.isPresent()) {
//            Review blogReview = result.get();
//            blogReview.changeGrade(blogReviewDTO.getGrade());
//            blogReview.changeText(blogReviewDTO.getText());
//            reviewRepository.save(blogReview);
//        }
    }

    @Override
    public void removeBlogReview(Long reviewnum) {
        reviewRepository.deleteById(reviewnum);
    }
}

