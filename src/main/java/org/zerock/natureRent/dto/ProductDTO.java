package org.zerock.natureRent.dto;


import lombok.*;
import org.zerock.natureRent.entity.Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductDTO {

    private Long mno;

    private String title;
    private String detail;
    private BigDecimal price;

    @Builder.Default
    private List<ProductImageDTO> imageDTOList = new ArrayList<>();

    //영화의 평균 평점
    private double avg;

    //리뷰 수 jpa의 count( )
    private int reviewCnt;

    private LocalDateTime regDate;
    private LocalDateTime modDate;


    // 렌탈 날짜 추가
    private LocalDateTime rentalStartDate;
    private LocalDateTime rentalEndDate;

    private boolean isAvailable;

    // 예약 관련 필드 추가
    private LocalDateTime rentalReserveStart;
    private LocalDateTime rentalReserveEnd;

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    // 엔티티를 DTO로 변환하는 메서드
    public static ProductDTO fromEntity(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setMno(product.getMno());
        dto.setTitle(product.getTitle());
        dto.setDetail(product.getDetail());
        dto.setPrice(product.getPrice());
//        dto.setAvg(product.getAvg());
//        dto.setReviewCnt(product.getReviewCnt());
        dto.setRegDate(product.getRegDate());
        // 필요한 필드 추가 설정...
        return dto;
    }

}
