package org.zerock.natureRent.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zerock.natureRent.dto.ProductDTO;
import org.zerock.natureRent.dto.ProductImageDTO;
import org.zerock.natureRent.dto.PageRequestDTO;
import org.zerock.natureRent.dto.PageResultDTO;
import org.zerock.natureRent.entity.Blog;
import org.zerock.natureRent.entity.Product;
import org.zerock.natureRent.entity.ProductImage;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface ProductService {

    Long register(ProductDTO productDTO);

    PageResultDTO<ProductDTO, Object[]> getList(PageRequestDTO requestDTO); //목록 처리

    ProductDTO getProduct(Long mno);

    List<LocalDateTime> getRentedDates(Long mno);

//    Page<Blog> findAllProducts(Pageable pageable);

    default ProductDTO entitiesToDTO(Product product, List<ProductImage> productImages, Double avg, Long reviewCnt){
        ProductDTO productDTO = ProductDTO.builder()
                .mno(product.getMno())
                .title(product.getTitle())
                .regDate(product.getRegDate())
                .modDate(product.getModDate())
                .rentalStartDate(product.getRentalStartDate())
                .rentalEndDate(product.getRentalEndDate())
                .isAvailable(product.isAvailable())
                .price(product.getPrice())  // price 필드 설정
                .build();

        List<ProductImageDTO> productImageDTOList = productImages.stream().map(productImage -> {
            return ProductImageDTO.builder().imgName(productImage.getImgName())
                    .path(productImage.getPath())
                    .uuid(productImage.getUuid())
                    .build();
        }).collect(Collectors.toList());

        productDTO.setImageDTOList(productImageDTOList);
        productDTO.setAvg(avg);
        productDTO.setReviewCnt(reviewCnt.intValue());



        return productDTO;

    }

//    public List<LocalDateTime> getRentedDates(Long mno) {
//        // 이 메서드는 특정 상품(mno)의 렌탈 불가능한 날짜 리스트를 반환
//        return productRepository.findRentedDatesByProductId(mno);
//    }


    default Map<String, Object> dtoToEntity(ProductDTO productDTO){

        Map<String, Object> entityMap = new HashMap<>();

        Product product = Product.builder()
                .mno(productDTO.getMno())
                .title(productDTO.getTitle())
                .rentalStartDate(productDTO.getRentalStartDate())
                .rentalEndDate(productDTO.getRentalEndDate())
                .isAvailable(productDTO.isAvailable()) // boolean 필드 바로 사용
                .price(productDTO.getPrice())  // price 필드 설정
                .build();

        entityMap.put("product", product);

        List<ProductImageDTO> imageDTOList = productDTO.getImageDTOList();

        if(imageDTOList != null && imageDTOList.size() > 0 ) { //ProductImageDTO 처리

            List<ProductImage> productImageList = imageDTOList.stream().map(productImageDTO ->{

                ProductImage productImage = ProductImage.builder()
                        .path(productImageDTO.getPath())
                        .imgName(productImageDTO.getImgName())
                        .uuid(productImageDTO.getUuid())
                        .product(product)
                        .build();
                return productImage;
            }).collect(Collectors.toList());

            entityMap.put("imgList", productImageList);
        }

        return entityMap;
    }

}
