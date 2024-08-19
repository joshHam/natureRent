package org.zerock.natureRent.entity;

import lombok.*;

import jakarta.persistence.*;

@Entity
@Embeddable
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString(exclude = "product") //연관 관계시 항상 주의
public class ProductImage  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inum;

    private String uuid;

    private String imgName;

    private String path;

    @ManyToOne(fetch = FetchType.LAZY) //무조건 lazy로
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    private Blog blog;

    @ManyToOne(fetch = FetchType.LAZY)
    private Cart cart;
}
