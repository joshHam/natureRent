package org.zerock.natureRent.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.zerock.natureRent.dto.ReviewDTO;
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

        return result.stream().map(productReview -> entityToDto(productReview)).collect(Collectors.toList());
    }

    @Override
    public Long register(ReviewDTO productReviewDTO) {

        Review productReview = dtoToEntity(productReviewDTO);

        reviewRepository.save(productReview);

        return productReview.getReviewnum();
    }

    @Override
    public void modify(ReviewDTO productReviewDTO) {

        Optional<Review> result =
                reviewRepository.findById(productReviewDTO.getReviewnum());

        if(result.isPresent()){

            Review productReview = result.get();
            productReview.changeGrade(productReviewDTO.getGrade());
            productReview.changeText(productReviewDTO.getText());

            reviewRepository.save(productReview);
        }

    }

    @Override
    public void remove(Long reviewnum) {

        reviewRepository.deleteById(reviewnum);

    }
}

