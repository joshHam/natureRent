package org.zerock.natureRent.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.natureRent.dto.ProductDTO;
import org.zerock.natureRent.dto.PageRequestDTO;
import org.zerock.natureRent.dto.PageResultDTO;
import org.zerock.natureRent.dto.ProductImageDTO;
import org.zerock.natureRent.entity.Product;
import org.zerock.natureRent.entity.ProductImage;
import org.zerock.natureRent.repository.ProductImageRepository;
import org.zerock.natureRent.repository.ProductRepository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Log4j2
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository; //final
    private final ProductImageRepository imageRepository; //final

    @Transactional
    @Override
    public Long register(ProductDTO productDTO) {

        Map<String, Object> entityMap = dtoToEntity(productDTO);
        Product product = (Product) entityMap.get("product");
        List<ProductImage> productImageList = (List<ProductImage>) entityMap.get("imgList");

        productRepository.save(product);

        productImageList.forEach(productImage -> {
            imageRepository.save(productImage);
        });

        return product.getMno();
    }
    @Override
    public PageResultDTO<ProductDTO, Object[]> getList(PageRequestDTO requestDTO) {

        Pageable pageable = requestDTO.getPageable(Sort.by("mno").descending());

        Page<Object[]> result = productRepository.getListPage(pageable);

        log.info("==============================================");
        result.getContent().forEach(arr -> {
            log.info(Arrays.toString(arr));
        });


        Function<Object[], ProductDTO> fn = (arr -> entitiesToDTO(
                (Product)arr[0] ,
                (List<ProductImage>)(Arrays.asList((ProductImage)arr[1])),
                (Double) arr[2],
                (Long)arr[3])
        );

        return new PageResultDTO<>(result, fn);
    }



    @Override
    public ProductDTO getProduct(Long mno) {

        // 쿼리 실행 전 로그 출력
        log.info("Fetching product and related images for mno: {}", mno);
        List<Object[]> result = productRepository.getProductWithAll(mno);

        // 쿼리 실행 후 로그 출력
        if (result.isEmpty()) {
            log.warn("No product or images found for mno: {}", mno);
        } else {
            log.info("Product and images fetched successfully for mno: {}. Result size: {}", mno, result.size());
        }

        Product product = (Product) result.get(0)[0];

        List<ProductImage> productImageList = new ArrayList<>();

        result.forEach(arr -> {
            ProductImage  productImage = (ProductImage)arr[1];
//            productImageList.add(productImage);
            if (productImage != null) {
                productImageList.add(productImage);
                // 개별 이미지에 대한 로그 출력
                log.info("Image found: UUID = {}, Path = {}", productImage.getUuid(), productImage.getPath());
            } else {
                log.warn("Null image found for product mno: {}", mno);
            }
        });

        Double avg = (Double) result.get(0)[2];
        Long reviewCnt = (Long) result.get(0)[3];

        return entitiesToDTO(product, productImageList, avg, reviewCnt);
    }
    @Override
    public ProductDTO entitiesToDTO(Product product, List<ProductImage> productImages, Double avg, Long reviewCnt) {
        log.info("Converting entities to DTO for product: {}", product.getMno());

        ProductDTO productDTO = ProductDTO.builder()
                .mno(product.getMno())
                .title(product.getTitle())
                .regDate(product.getRegDate())
                .modDate(product.getModDate())
                .avg((avg != null) ? avg : 0.0)
                .reviewCnt((reviewCnt != null) ? reviewCnt.intValue() : 0)
                .price(product.getPrice())
                .isAvailable(product.isAvailable())
                .build();

        List<ProductImageDTO> productImageDTOList = productImages.stream()
                .map(image -> {
                    log.info("Converting ProductImage to ProductImageDTO: UUID = {}, Path = {}", image.getUuid(), image.getPath());
                    return ProductImageDTO.builder()
                            .imgName(image.getImgName())
                            .path(image.getPath())
                            .uuid(image.getUuid())
                            .build();
                }).collect(Collectors.toList());

        productDTO.setImageDTOList(productImageDTOList);

        if (productDTO.getImageDTOList().isEmpty()) {
            log.warn("ImageDTOList is empty after conversion for product: {}", product.getMno());
        }

        return productDTO;
    }

    @Override
    public Map<String, Object> dtoToEntity(ProductDTO productDTO) {
        Map<String, Object> entityMap = new HashMap<>();

        Product product = Product.builder()
                .mno(productDTO.getMno())
                .title(productDTO.getTitle())
                .isAvailable(productDTO.isAvailable())
                .price(productDTO.getPrice())
                .build();

        entityMap.put("product", product);

        List<ProductImageDTO> imageDTOList = productDTO.getImageDTOList();

        if (imageDTOList != null && !imageDTOList.isEmpty()) {
            List<ProductImage> productImageList = imageDTOList.stream().map(dto ->
                    ProductImage.builder()
                            .path(dto.getPath())
                            .imgName(dto.getImgName())
                            .uuid(dto.getUuid())
                            .product(product)
                            .build()
            ).collect(Collectors.toList());

            entityMap.put("imgList", productImageList);
        }

        return entityMap;
    }

    @Override
    public PageResultDTO<ProductDTO, Product> searchProducts(String keyword, int page) {
        Pageable pageable = PageRequest.of(page - 1, 10); // 한 페이지에 10개씩 보여주기

        Page<Product> result;
        // 검색어가 없을 때는 전체 목록 반환
        if (keyword == null || keyword.isEmpty()) {
            result = productRepository.findAll(pageable); // 전체 제품 목록
        } else {
            // 제목이나 상세에 검색어가 포함된 항목 검색
            result = productRepository.findByTitleContainingOrDetailContaining(keyword, keyword, pageable);
        }

        // PageResultDTO로 변환하여 반환, Product 엔티티를 DTO로 변환
        return new PageResultDTO<>(result, ProductDTO::fromEntity);
    }
}


















