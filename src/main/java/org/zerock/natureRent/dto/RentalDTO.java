package org.zerock.natureRent.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RentalDTO {

    private Long rno; // Rental 번호
    private LocalDateTime rentalStartDate; // 렌탈 시작 날짜
    private LocalDateTime rentalEndDate; // 렌탈 종료 날짜
    private Long mno; // 해당 상품 번호 (Product와의 관계)
    private String email; // 해당 고객 이메일 (Member와의 관계)
    private int quantity; // 렌탈 수량

}
