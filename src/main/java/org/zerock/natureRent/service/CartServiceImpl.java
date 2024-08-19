package org.zerock.natureRent.service;
//package org.zerock.natureRent.service.impl;

import lombok.RequiredArgsConstructor;
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
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductService productService; // ProductService 주입


    @Override
    public List<CartDTO> getCartList(String memberEmail) {
        List<Cart> carts = cartRepository.findByMemberEmail(memberEmail);
        return carts.stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public CartDTO entityToDTO(Cart cart) {
        Hibernate.initialize(cart.getProductImages()); // 지연 로딩된 productImages 초기화
        Hibernate.initialize(cart.getProduct());

        // ProductImageDTO 대신 ProductImage 사용
        List<ProductImage> imageList = cart.getProductImages(); // 이미 productImages가 List<ProductImage> 타입이야

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
}