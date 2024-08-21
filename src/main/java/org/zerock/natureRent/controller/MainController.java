package org.zerock.natureRent.controller;


import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.zerock.natureRent.entity.Cart;
import org.zerock.natureRent.entity.Member;
import org.zerock.natureRent.entity.Product;
import org.zerock.natureRent.repository.CartRepository;
import org.zerock.natureRent.repository.ProductRepository;
import org.zerock.natureRent.security.dto.MemberDTO;
import org.zerock.natureRent.dto.ProductDTO;
import org.zerock.natureRent.dto.PageRequestDTO;
import org.zerock.natureRent.dto.PageResultDTO;
import org.zerock.natureRent.service.CartService;
import org.zerock.natureRent.service.ProductService;
import org.zerock.natureRent.dto.CartDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Log4j2
@RequestMapping("/main/")
public class MainController {

    private final ProductService productService;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartService cartService;

    public MainController(ProductService productService, CartRepository cartRepository, ProductRepository productRepository, CartService cartService) {
        this.productService = productService;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.cartService = cartService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/all")
    public String exAll(Model model, PageRequestDTO pageRequestDTO,@AuthenticationPrincipal MemberDTO authMember){
        log.info("exAll..........");
        PageResultDTO<ProductDTO, Object[]> result = productService.getList(pageRequestDTO);
        model.addAttribute("result", result);

        Member member = authMember.getMember();
        List<CartDTO> cartList = cartService.getCartList(member.getEmail()); // cartService를 사용해 CartDTO 리스트를 가져옴
        model.addAttribute("cartList", cartList);

        return "main/all";
    }

    /*@GetMapping("/product-details")
    public String exProductDetails(Model model, PageRequestDTO pageRequestDTO){
        log.info("exProductDetails..........");
        PageResultDTO<ProductDTO, Object[]> result = productService.getList(pageRequestDTO);
        model.addAttribute("result", result);

        return "main/product-details";
    }*/

//    @GetMapping({"/read", "/modify"})




    @GetMapping("cart")
    public String exCart(){
        log.info("exCart..........");
        return "/main/cart"; // 명시적으로 뷰 이름을 반환

    }

    @GetMapping("about-us")
    public String exAboutUs(){
        log.info("exAboutUs..........");
        return "/main/about-us"; // 명시적으로 뷰 이름을 반환

    }

    @GetMapping("checkout")
    public String exCheckOut(){
        log.info("exCheckOut..........");
        return "/main/checkout"; // 명시적으로 뷰 이름을 반환
    }

    @GetMapping("faq")
    public String exFaq(){
        log.info("exFaq..........");
        return "/main/faq"; // 명시적으로 뷰 이름을 반환

    }


    //로그인한 사용자중에 user95@zerock.org만 접근가능하도록//
    @PreAuthorize("#clubAuthMember != null && #clubAuthMember.username eq \"user95@zerock.org\"")
    @GetMapping("/exOnly")
    public String exMemberOnly(@AuthenticationPrincipal MemberDTO clubAuthMember){

        log.info("exMemberOnly.............");
        log.info(clubAuthMember);

        return "/main/admin";
    }






    @PostMapping("/cart/add")
    public ResponseEntity<String> addToCart(
            @AuthenticationPrincipal MemberDTO authMember, // 로그인한 사용자 정보
            @RequestParam("productMno") Long productMno,
//            @RequestParam("rentalReserveStartDate") LocalDateTime rentalReserveStartDate,
//            @RequestParam("rentalReserveEndDate") LocalDateTime rentalReserveEndDate,
            @RequestParam("rentalReserveStartDate") LocalDate rentalReserveStartDate,  // 변경된 부분
            @RequestParam("rentalReserveEndDate") LocalDate rentalReserveEndDate,      // 변경된 부분
            @RequestParam("quantity") int quantity) {

        // 필요에 따라 LocalDate를 LocalDateTime으로 변환할 수도 있음
        LocalDateTime startDateTime = rentalReserveStartDate.atStartOfDay();
        LocalDateTime endDateTime = rentalReserveEndDate.atStartOfDay();


        // 현재 로그인한 사용자 정보 가져오기
        Member member = authMember.getMember();
        // 선택된 제품 정보 가져오기
        Product product = productRepository.findById(productMno).orElseThrow(() -> new IllegalArgumentException("Invalid product ID"));

        // Cart 엔티티 생성 및 저장
        Cart cart = Cart.builder()
                .member(member)// Member 객체 설정
                .product(product)
                .rentalStartDate(startDateTime)
                .rentalEndDate(endDateTime)
                .quantity(quantity)
                .build();

        cartRepository.save(cart);

//        return "/product/product-grids";
        return ResponseEntity.ok("Item added to cart successfully.");
    }


    @GetMapping("/cart/items")
    public String getCartList(Model model, @AuthenticationPrincipal MemberDTO authMember) {
        log.info("Entering getCartList method"); // 메서드 진입 확인
//        PageResultDTO<ProductDTO, Object[]> result = productService.getList(pageRequestDTO);

        // 현재 로그인한 사용자 가져오기
        if (authMember == null) {
            log.warn("authMember is null");
        } else {
            log.info("Fetching cart for user: {}", authMember.getEmail());
        }
        Member member = authMember.getMember();

        if (member != null) {
            // 해당 사용자의 Cart 데이터 가져오기
            log.info("Member found: {}", member.getEmail());
            List<CartDTO> cartList = cartService.getCartList(member.getEmail()); // cartService를 사용해 CartDTO 리스트를 가져옴

            if (cartList.isEmpty()) {
                log.info("No cart items found for user: {}", member.getEmail());
            } else {
                log.info("Number of cart items found: {}", cartList.size());

                // 각 카트 아이템의 상세 정보를 로그로 출력
                cartList.forEach(cart -> {
                    log.info("Cart Item: {}", cart);
                    if (cart.getProductDTO() != null) {
                        log.info("Product in Cart: {}", cart.getProductDTO());
                        log.info("Product MNO: {}", cart.getProductDTO().getMno());

                        // 이미지 리스트 정보 로그 출력
                        if (cart.getProductDTO().getImageDTOList() != null && !cart.getProductDTO().getImageDTOList().isEmpty()) {
                            log.info("Number of images in product: {}", cart.getProductDTO().getImageDTOList().size());
                            cart.getProductDTO().getImageDTOList().forEach(imageDTO -> {
                                log.info("Image Thumbnail URL: {}", imageDTO.getThumbnailURL());
                            });
                        // 이제 CartDTO에서 imageDTOList를 직접 사용
//                        if (cart.getImageDTOList() != null && !cart.getImageDTOList().isEmpty()) {
//                            log.info("Number of images in cart item: {}", cart.getImageDTOList().size());
//                            cart.getImageDTOList().forEach(imageDTO -> {
//                                log.info("Image Thumbnail URL: {}", imageDTO.getThumbnailURL());
//                            });
                        } else {
                            log.warn("Image list is null or empty for product: {}", cart.getProductDTO().getMno());
                        }
                    } else {
                        log.warn("Product in cart is null for cart ID: {}", cart.getCno());
                    }
                });

            }

            // 모델에 Cart 리스트 추가
            model.addAttribute("cartList", cartList);
        }else {
            log.warn("No member found for the current session");
        }

        return "main/cart";// cart.html로 렌더링
    }

    //일반적으로 HTTP GET 요청은 데이터를 삭제하는 데 사용하지 않는 것이 RESTful 원칙에 맞아
    @PostMapping("/cart/remove")
    public String removeItem(@RequestParam("cno") Long cno) {
        cartService.removeItem(cno);
        return "redirect:/main/cart/items";  // 장바구니 페이지로 리다이렉트
    }


//    @GetMapping("/remove")
//    public String removeItem(@RequestParam("cno") Long cno) {
//        cartService.removeItem(cno);
//        return "redirect:/cart";  // 장바구니 페이지로 리다이렉트
//    }





}
