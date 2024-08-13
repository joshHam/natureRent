package org.zerock.natureRent.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BlogDTO {

    private Long bno;
    private String title;
    private String detail;
    private int likes;
    private int views;
    private String tags;
    private String memberEmail; // Member의 email을 가져오기 위한 필드

    // 필요하다면 추가 필드와 메서드를 작성할 수 있음
}
