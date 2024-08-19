package org.zerock.natureRent.service;
//package org.zerock.natureRent.service;

import org.zerock.natureRent.dto.CartDTO;
import org.zerock.natureRent.entity.Cart;
import org.zerock.natureRent.entity.Member;

import java.util.List;

public interface CartService {
//    List<Cart> getCartItems(Member member);

    List<CartDTO> getCartList(String memberEmail); // 회원 이메일을 통해 카트 리스트를 가져오는 메서드
    CartDTO entityToDTO(Cart cart); // Cart 엔티티를 CartDTO로 변환하는 메서드

}
