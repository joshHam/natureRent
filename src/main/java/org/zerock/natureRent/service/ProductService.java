package org.zerock.natureRent.service;

import org.zerock.natureRent.dto.ProductDTO;
import org.zerock.natureRent.dto.ProductImageDTO;
import org.zerock.natureRent.dto.PageRequestDTO;
import org.zerock.natureRent.dto.PageResultDTO;
import org.zerock.natureRent.entity.Product;
import org.zerock.natureRent.entity.ProductImage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface ProductService {

    Long register(ProductDTO productDTO);

    PageResultDTO<ProductDTO, Object[]> getList(PageRequestDTO requestDTO); //목록 처리

    ProductDTO getProduct(Long mno);

    default ProductDTO entitiesToDTO(Product product, List<ProductImage> productImages, Double avg, Long reviewCnt){
        ProductDTO productDTO = ProductDTO.builder()
                .mno(product.getMno())
                .title(product.getTitle())
                .regDate(product.getRegDate())
                .modDate(product.getModDate())
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

    default Map<String, Object> dtoToEntity(ProductDTO productDTO){

        Map<String, Object> entityMap = new HashMap<>();

        Product product = Product.builder()
                .mno(productDTO.getMno())
                .title(productDTO.getTitle())
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
