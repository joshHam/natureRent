package org.zerock.natureRent.dto;

import lombok.*;
import org.zerock.natureRent.security.dto.MemberDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BlogDTO {

    private Long bno;
    private String title;
    private String detail;
    private int likes;
    private int views;
    private String tags;
    private MemberDTO member;  // MemberDTO 추가
    //Member email
    private String member_email;
 // Member의 email을 가져오기 위한 필드
    private LocalDateTime regDate;
    private LocalDateTime modDate;

    @Builder.Default
    private List<ProductImageDTO> imageDTOList = new ArrayList<>();

    // 필요하다면 추가 필드와 메서드를 작성할 수 있음
}
