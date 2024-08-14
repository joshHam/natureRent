package org.zerock.natureRent.entity;

import lombok.*;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mno;

    private String title;

   /* @Column(name = "rental_start_date")
    private LocalDateTime rentalStartDate;
    @Column(name = "rental_end_date")
    private LocalDateTime rentalEndDate;*/
   private boolean isAvailable;

   /* @Column(name = "rental_reserve_start")
    private LocalDateTime rentalReserveStart;
    @Column(name = "rental_reserve_end")
    private LocalDateTime rentalReserveEnd;
*/
    // 추가된 price 필드
    @Column(nullable = false)
    private BigDecimal price;

    // 상품 상세 설명을 위한 필드 추가
    @Lob
    private String detail;
}
