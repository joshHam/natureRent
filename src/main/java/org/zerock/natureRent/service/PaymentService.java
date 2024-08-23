package org.zerock.natureRent.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.natureRent.dto.RentalSaveDTO;
import org.zerock.natureRent.entity.Cart;
import org.zerock.natureRent.entity.Member;
import org.zerock.natureRent.entity.Product;
import org.zerock.natureRent.entity.Rental;
import org.zerock.natureRent.repository.CartRepository;
import org.zerock.natureRent.repository.MemberRepository;
import org.zerock.natureRent.repository.ProductRepository;
import org.zerock.natureRent.repository.RentalRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final RentalRepository rentalRepository; // 렌탈 정보를 저장하는 레포지토리
    private final ProductRepository productRepository; // 상품 정보를 가져오는 레포지토리
    private final MemberRepository memberRepository; // 유저 정보를 가져오는 레포지토리
    private final CartRepository cartRepository; // Cart 데이터를 처리하기 위한 레포지토리 추가

    /**
     * 결제가 완료되면 렌탈 정보를 저장하고, Cart 데이터를 삭제하는 메서드
     *
     * @param customerEmail 고객 이메일
     * @param rentalSaveDtos 렌탈 정보 DTO 리스트
     */
    @Transactional
    public void saveRental(String customerEmail, List<RentalSaveDTO> rentalSaveDtos) {
        try {
            Member customer = memberRepository.findByEmail(customerEmail, false)
                    .orElseThrow(() -> new RuntimeException("고객을 찾을 수 없습니다."));

            for (RentalSaveDTO dto : rentalSaveDtos) {
                if (dto.getMno() == null) {
                    throw new IllegalArgumentException("Product ID must not be null");
                }

                Product product = productRepository.findById(dto.getMno())
//                        .orElseThrow(() -> new IllegalArgumentException("Product ID must not be null or empty"));
                        .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다."));

                Rental rental = Rental.builder()
                        .customer(customer)
                        .product(product)
                        .rentalStartDate(dto.getRentalStartDate())
                        .rentalEndDate(dto.getRentalEndDate())
                        .quantity(dto.getQuantity())
                        .orderPrice(dto.getOrderPrice())
                        .receiverName(dto.getReceiverName())
                        .phoneNumber(dto.getPhoneNumber())
                        .orderNumber(dto.getOrderNumber())
                        .zipcode(dto.getZipcode())
                        .address(dto.getAddress())
                        .orderRequired(dto.getOrderRequired())
                        .paymentMethod(dto.getPaymentMethod())
                        .build();

                rentalRepository.save(rental);
                log.info("렌탈이 저장되었습니다. 주문 번호: {}", dto.getRno());

                // 관련 Cart 항목 삭제
                cartRepository.deleteByMemberEmailAndProductMno(customerEmail, product.getMno());
                log.info("Cart 항목이 삭제되었습니다. 상품 번호: {}", product.getMno());
            }
        } catch (Exception e) {
            log.error("렌탈 저장 중 오류 발생", e);
            throw new RuntimeException("렌탈 저장 중 오류가 발생했습니다.", e);
        }
    }
}

