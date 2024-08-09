package org.zerock.natureRent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.natureRent.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
    // 추가적인 메소드 정의 가능
}
