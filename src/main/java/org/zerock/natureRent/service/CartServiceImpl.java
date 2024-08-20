package org.zerock.natureRent.service;
//package org.zerock.natureRent.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.natureRent.dto.CartDTO;
import org.zerock.natureRent.dto.ProductImageDTO;
import org.zerock.natureRent.entity.Cart;
import org.zerock.natureRent.entity.Member;
import org.zerock.natureRent.entity.ProductImage;
import org.zerock.natureRent.repository.CartRepository;
import org.zerock.natureRent.service.CartService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductService productService; // ProductService 주입


    @Override
    public List<CartDTO> getCartList(String memberEmail) {
        log.info("Fetching cart list for user: {}", memberEmail);
        List<Cart> carts = cartRepository.findByMemberEmailWithProducts(memberEmail);


        // Cart -> CartDTO 변환
        List<CartDTO> cartDTOList = carts.stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());

        // 변환된 CartDTO 리스트 반환
        return cartDTOList;
//        return carts.stream()
//                .map(this::entityToDTO)
//                .collect(Collectors.toList());
//
//        carts.forEach(cart -> {
//            log.info("CartDTO created for cart ID: {}", cart.getCno());
//            if (cart.getProduct() != null) {
//                log.info("ProductDTO found in CartDTO for product MNO: {}", cart.getProduct().getMno());
//
//                if (cart.getProduct().getImageList() != null && !cart.getProduct().getImageList().isEmpty()) {
//                    log.info("ProductDTO contains {} images", cart.getProduct().getImageList().size());
//                } else {
//                    log.warn("ProductDTO has an empty image list");
//                }
//            } else {
//                log.warn("ProductDTO is null for cart ID: {}", cart.getCno());
//            }
//        });
//
//        return carts;
    }

    @Transactional
    @Override
    public CartDTO entityToDTO(Cart cart) {
//        Hibernate.initialize(cart.getProductImages()); // 지연 로딩된 productImages 초기화
//        Hibernate.initialize(cart.getProduct());
        // 로그 추가: cart.getProduct()와 cart.getProductImages()가 올바르게 초기화되었는지 확인
        log.info("Converting Cart entity to DTO, Cart ID: {}", cart.getCno());
        log.info("Product in Cart: {}", cart.getProduct());

        // ProductImageDTO 대신 ProductImage 사용
//        List<ProductImage> imageList = cart.getProductImages(); // 이미 productImages가 List<ProductImage> 타입이야
//        if (imageList == null || imageList.isEmpty()) {
//            log.warn("(CSI)Image list is null or empty for product: {}", cart.getProduct().getMno());
//        } else {
//            log.info("Number of images in product: {}", imageList.size());
//            imageList.forEach(image -> log.info("Image Details - UUID: {}, Path: {}, Image Name: {}", image.getUuid(), image.getPath(), image.getImgName()));
//        }
        // Product에서 이미지를 가져옴
        List<ProductImage> imageList = cart.getProduct().getImageList();
        if (imageList == null || imageList.isEmpty()) {
            log.warn("Image list is null or empty for product: {}", cart.getProduct().getMno());
        } else {
            log.info("Number of images in product: {}", imageList.size());
            imageList.forEach(image -> log.info("Image Details - UUID: {}, Path: {}, Image Name: {}", image.getUuid(), image.getPath(), image.getImgName()));
        }

        // 이걸 ProductService의 entitiesToDTO 메서드에 전달
        return CartDTO.builder()
                .cno(cart.getCno())
                .memberEmail(cart.getMember().getEmail())
//                .productDTO(ProductService.entitiesToDTO(cart.getProduct(), imageList, null, null)) // 여기서 imageList는 List<ProductImage> 타입이어야 해
                .productDTO(productService.entitiesToDTO(cart.getProduct(), imageList, null, null))  // 인스턴스 메서드 호출
                .rentalStartDate(cart.getRentalStartDate())
                .rentalEndDate(cart.getRentalEndDate())
                .quantity(cart.getQuantity())
                .build();
    }

    @Override
    public void removeItem(Long cno) {
        cartRepository.deleteById(cno);
    }
}