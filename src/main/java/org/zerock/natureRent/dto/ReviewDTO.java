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
public class ReviewDTO {

    //review num
    private Long reviewnum;

    //Product mno
    private Long mno;

    //Membmer id
    private Long mid;
    //Member nickname
//    private String nickname;
    private String name;
    //Member email
    private String member_email;

    private int grade;

    private String text;

    private LocalDateTime regDate, modDate;


}
