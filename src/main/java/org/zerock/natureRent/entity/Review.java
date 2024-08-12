package org.zerock.natureRent.entity;

import lombok.*;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = {"product","member"})
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewnum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_mno")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_email")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blog_bno")
    private Blog blog;

    @Column(nullable = true)  // nullable 설정
    private Integer grade;  // Integer로 변경하여 null 허용
    private String text;

    public void changeGrade(int grade){
        this.grade = grade;
    }

    public void changeText(String text){
        this.text = text;
    }

//    @ManyToOne 어노테이션과 @JoinColumn 어노테이션을 사용하여 외래 키를 정의합니다.
//    @ManyToOne(fetch = FetchType.LAZY): 다대일 관계를 설정하며, 지연 로딩을 적용합니다.
//    @JoinColumn(name = "blog_bno"): Review 테이블의 blog_bno 컬럼이 Blog 테이블의 기본 키(bno)를 참조하도록 설정합니다.

    @PrePersist
    public void prePersist() {
        this.setRegDate(LocalDateTime.now());
    }

    @PreUpdate
    public void preUpdate() {
        this.setModDate(LocalDateTime.now());
    }



}
