package org.zerock.natureRent.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.natureRent.entity.Member;
import org.zerock.natureRent.entity.Product;
import org.zerock.natureRent.entity.Review;

import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
public class ReviewRepositoryTests {

    @Autowired
    private ReviewRepository reviewRepository;



    @Test
    public void insertProductwReviews() {

        //200개의 리뷰를 등록
        IntStream.rangeClosed(1,250).forEach(i -> {

            //영화 번호
            Long mno = (long)(Math.random()*100) + 1;

            //리뷰어 번호
            long mid  =  ((long)(Math.random()*100) + 1 );
            String email = "user"+mid+"@zerock.org";
            Member member = Member.builder().email(email).build();

            Review productReview = Review.builder()
                    .member(member)
                    .product(Product.builder().mno(mno).build())
                    .grade((int)(Math.random()* 5) + 1)
                    .text("이 영화에 대한 느낌..."+i)
                    .build();

            reviewRepository.save(productReview);
        });
    }


    @Test
    public void testGetProductReviews() {

        Product product = Product.builder().mno(93L).build();

        List<Review> result = reviewRepository.findByProduct(product);

        result.forEach(productReview -> {

            System.out.print(productReview.getReviewnum());
            System.out.print("\t"+productReview.getGrade());
            System.out.print("\t"+productReview.getText());
            System.out.print("\t"+productReview.getMember().getEmail());
            System.out.println("---------------------------");
        });
    }

}
