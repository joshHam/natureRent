package org.zerock.natureRent.dto;

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



//    private int quantity;
}
