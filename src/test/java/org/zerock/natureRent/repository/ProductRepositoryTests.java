package org.zerock.natureRent.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.natureRent.entity.Product;
import org.zerock.natureRent.entity.ProductImage;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

@SpringBootTest
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductImageRepository imageRepository;

    @Commit
    @Transactional
    @Test
    public void insertProducts() {

        IntStream.rangeClosed(1,20).forEach(i -> {

            Product product = Product.builder().title("Product...." +i).build();

            System.out.println("------------------------------------------");

            productRepository.save(product);

            int count = (int)(Math.random() * 5) + 1; //1,2,3,4


            for(int j = 0; j < count; j++){
                ProductImage productImage = ProductImage.builder()
                        .uuid(UUID.randomUUID().toString())
                        .product(product)
                        .imgName("test"+j+".jpg").build();

                imageRepository.save(productImage);
            }


            System.out.println("===========================================");

        });
    }

    @Test
    public void testListPage(){

        PageRequest pageRequest = PageRequest.of(2,10, Sort.by(Sort.Direction.DESC, "mno"));

        Page<Object[]> result = productRepository.getListPage(pageRequest);

        for (Object[] objects : result.getContent()) {
            System.out.println(Arrays.toString(objects));
        }
    }

    @Test
    public void testGetProductWithAll() {

        List<Object[]> result = productRepository.getProductWithAll(92L);

        System.out.println(result);

        for (Object[] arr : result) {
            System.out.println(Arrays.toString(arr));
        }

    }

}

