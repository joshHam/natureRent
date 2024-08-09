package org.zerock.natureRent.entity;

import lombok.*;
import jakarta.persistence.*;


@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter  // 추가: 필드의 setter 메서드를 생성
@ToString
public class Blog extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;

    private String title;

    @Lob
    private String detail;

    private int likes;
    private int views;

    private String tags;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_email")
    private Member member;
}
