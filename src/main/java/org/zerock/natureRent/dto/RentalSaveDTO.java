package org.zerock.natureRent.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class RentalSaveDTO {

    private Long rno; // Rental 번호
    private LocalDateTime rentalStartDate; // 렌탈 시작 날짜
    private LocalDateTime rentalEndDate; // 렌탈 종료 날짜
    private Long mno; // 해당 상품 번호 (Product와의 관계)
    private String email; // 해당 고객 이메일 (Member와의 관계)
    private int quantity; // 렌탈 수량

    private int orderPrice;             // 예약 가격
    private String receiverName;        // 수령인 이름
    private String phoneNumber;         // 연락처
    private String orderNumber;         // 주문 번호
    private int zipcode;                // 우편번호
    private String address;             // 주소
    private String orderRequired;       // 특별 요청사항
    private String paymentMethod;       // 결제 방식
//    private String customerEmail;       // 고객 이메일

}
