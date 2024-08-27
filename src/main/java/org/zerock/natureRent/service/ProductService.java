package org.zerock.natureRent.service;


import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
//    private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);
final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);


    Long register(ProductDTO productDTO);

    PageResultDTO<ProductDTO, Object[]> getList(PageRequestDTO requestDTO);

    ProductDTO getProduct(Long mno);

    // DTO to Entity 변환 메서드
    Map<String, Object> dtoToEntity(ProductDTO productDTO);

    // Entity to DTO 변환 메서드
    ProductDTO entitiesToDTO(Product product, List<ProductImage> productImages, Double avg, Long reviewCnt);




}
