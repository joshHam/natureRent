package org.zerock.natureRent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.natureRent.entity.Cart;
import org.zerock.natureRent.entity.Member;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {

    // 현재 로그인된 사용자의 Cart 항목들을 가져오는 메서드
    List<Cart> findByMember(Member member);

    List<Cart> findByMemberEmail(String memberEmail);
}
