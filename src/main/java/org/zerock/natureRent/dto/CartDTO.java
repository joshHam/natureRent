package org.zerock.natureRent.dto;
import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CartDTO {
    private Long cno;
    private String memberEmail;
    private ProductDTO productDTO; // 이 필드가 필요(getProductDTO)
    private int quantity;
    private List<ProductImageDTO> imageDTOList;
    private LocalDateTime rentalStartDate;

    private LocalDateTime rentalEndDate;

    // 기간에 따른 총 비용 계산
    public long getRentalDays() {
        return ChronoUnit.DAYS.between(rentalStartDate, rentalEndDate) + 1; // 시작일과 종료일 포함
    }

    public BigDecimal getTotalPrice() {
        return productDTO.getPrice().multiply(BigDecimal.valueOf(quantity)).multiply(BigDecimal.valueOf(getRentalDays()));
    }

//    private int quantity;
}
