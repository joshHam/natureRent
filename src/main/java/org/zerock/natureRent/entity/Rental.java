package org.zerock.natureRent.entity;

import lombok.*;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Rental extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;  // 예약 번호 (Primary Key)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_mno", nullable = false)
    private Product product;  // 상품과의 관계 설정 (Many-to-One)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_email", nullable = false)
    private Member customer;  // 고객과의 관계 설정 (Many-to-One)

    @Column(name = "rental_start_date", nullable = false)
    private LocalDateTime rentalStartDate;  // 예약 시작일

    @Column(name = "rental_end_date", nullable = false)
    private LocalDateTime rentalEndDate;  // 예약 종료일

    @Column(nullable = false, columnDefinition = "int default 1")
    private int quantity;  // 예약된 상품 수량

    @Column(nullable = false)
    private int orderPrice;  // 예약 가격

    @Column(nullable = false)
    private String receiverName;  // 수령인 이름

    @Column(nullable = false)
    private String phoneNumber;  // 연락처

    @Column(nullable = false)
    private String orderNumber;  // 주문 번호

    @Column(nullable = false)
    private int zipcode;  // 우편번호

    @Column(nullable = false)
    private String address;  // 주소

    @Column(nullable = true)
    private String orderRequired;  // 특별 요청사항

    @Column(nullable = false)
    private String paymentMethod;  // 결제 방식
}
