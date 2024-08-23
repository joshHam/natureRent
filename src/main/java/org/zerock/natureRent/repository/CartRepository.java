package org.zerock.natureRent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.natureRent.entity.Cart;
import org.zerock.natureRent.entity.Member;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {

    // 현재 로그인된 사용자의 Cart 항목들을 가져오는 메서드
    List<Cart> findByMember(Member member);
//    @Query("SELECT c FROM Cart c JOIN FETCH c.product p LEFT JOIN FETCH p.imageList WHERE c.member.email = :email")
//    List<Cart> findByMemberEmailWithProducts(@Param("email") String email);

//    @Query("SELECT c FROM Cart c LEFT JOIN FETCH c.product p LEFT JOIN FETCH p.imageList WHERE c.member.email = :memberEmail")
//    List<Cart> findByMemberEmailWithProducts(@Param("memberEmail") String memberEmail);
//    @Query("SELECT c FROM Cart c LEFT JOIN FETCH c.product WHERE c.member.email = :memberEmail")
//    List<Cart> findByMemberEmailWithProducts(@Param("memberEmail") String memberEmail);








//    이제 문제의 원인을 알 것 같아. product_image 테이블에서 이미지를 가져올 때
//    cart_cno가 없고, 대신 product_mno만 있다는 것은, 이미지는 Cart와 직접적인
//    관계가 없고 Product와 관계가 있다는 의미야. 그러니까 이미지를 가져올 때 Product를
//    통해서 가져와야 해.
//
//    지금 코드를 보면 Cart 엔티티 안에서 ProductImage를 직접 가져오려 하고 있어.
//    그러나 ProductImage는 Cart가 아닌 Product와 연관되어 있기 때문에,
//    Cart에서 직접 ProductImage를 가져오는 건 잘못된 접근이야.

//    CartRepository에서도 productImages를 직접 페치하려는 시도를 제거하고, 대신 Product만 가져오게 해야 해.
    @Query("SELECT c FROM Cart c LEFT JOIN FETCH c.product WHERE c.member.email = :memberEmail")
    List<Cart> findByMemberEmailWithProducts(@Param("memberEmail") String memberEmail);


    List<Cart> findByMemberEmail(String memberEmail);

    void deleteByMemberEmailAndProductMno(String customerEmail, Long mno);
}
