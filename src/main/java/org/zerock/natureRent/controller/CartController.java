//package org.zerock.natureRent.controller;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.zerock.natureRent.entity.Cart;
//import org.zerock.natureRent.entity.Member;
//import org.zerock.natureRent.entity.Product;
//import org.zerock.natureRent.repository.CartRepository;
//import org.zerock.natureRent.repository.ProductRepository;
//import org.zerock.natureRent.security.dto.MemberDTO;
////import org.zerock.natureRent.security.dto.MemberDTO;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Controller
//@Log4j2 // 로그를 사용하기 위해 추가
//@RequiredArgsConstructor
//public class CartController {
//
//    private final CartRepository cartRepository;
//    private final ProductRepository productRepository;
//
//    @PostMapping("/cart/add")
//    public ResponseEntity<String> addToCart(
//            @AuthenticationPrincipal MemberDTO authMember, // 로그인한 사용자 정보
//            @RequestParam("productMno") Long productMno,
////            @RequestParam("rentalReserveStartDate") LocalDateTime rentalReserveStartDate,
////            @RequestParam("rentalReserveEndDate") LocalDateTime rentalReserveEndDate,
//            @RequestParam("rentalReserveStartDate") LocalDate rentalReserveStartDate,  // 변경된 부분
//            @RequestParam("rentalReserveEndDate") LocalDate rentalReserveEndDate,      // 변경된 부분
//            @RequestParam("quantity") int quantity) {
//
//        // 필요에 따라 LocalDate를 LocalDateTime으로 변환할 수도 있음
//        LocalDateTime startDateTime = rentalReserveStartDate.atStartOfDay();
//        LocalDateTime endDateTime = rentalReserveEndDate.atStartOfDay();
//
//
//        // 현재 로그인한 사용자 정보 가져오기
//        Member member = authMember.getMember();
//        // 선택된 제품 정보 가져오기
//        Product product = productRepository.findById(productMno).orElseThrow(() -> new IllegalArgumentException("Invalid product ID"));
//
//        // Cart 엔티티 생성 및 저장
//        Cart cart = Cart.builder()
//                .member(member)// Member 객체 설정
//                .product(product)
//                .rentalStartDate(startDateTime)
//                .rentalEndDate(endDateTime)
//                .quantity(quantity)
//                .build();
//
//        cartRepository.save(cart);
//
//        return ResponseEntity.ok("Item added to cart successfully.");
//    }
//
//
//    @GetMapping("/cart")
//    public String getCartList(Model model, @AuthenticationPrincipal MemberDTO authMember) {
//        log.info("Entering getCartList method"); // 메서드 진입 확인
//
//        // 현재 로그인한 사용자 가져오기
//        if (authMember == null) {
//            log.warn("authMember is null");
//        } else {
//            log.info("Fetching cart for user: {}", authMember.getEmail());
//        }
//        Member member = authMember.getMember();
//
//        if (member != null) {
//            // 해당 사용자의 Cart 데이터 가져오기
//            log.info("Member found: {}", member.getEmail());
//            List<Cart> cartList = cartRepository.findByMember(member);
//            if (cartList.isEmpty()) {
//                log.info("No cart items found for user: {}", member.getEmail());
//            } else {
//                log.info("Number of cart items found: {}", cartList.size());
//            }
//
//            // 모델에 Cart 리스트 추가
//            model.addAttribute("cartList", cartList);
//        }else {
//            log.warn("No member found for the current session");
//        }
//
//        return "cart";// cart.html로 렌더링
//    }
//
//
//}
