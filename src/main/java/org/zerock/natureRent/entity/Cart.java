package org.zerock.natureRent.entity;

import lombok.*;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Cart extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_email")
    private Member member;

    // Product를 설정하는 메서드
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_mno")
    private Product product;


    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<ProductImage> productImages = new ArrayList<>();

    @Column(name = "rental_start_date", nullable = false)
    private LocalDateTime rentalStartDate;  // 예약 시작일

    @Column(name = "rental_end_date", nullable = false)
    private LocalDateTime rentalEndDate;  // 예약 종료일

    @Column(nullable = false, columnDefinition = "int default 1")
    private int quantity;

}
