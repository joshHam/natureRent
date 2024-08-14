package org.zerock.natureRent.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.natureRent.dto.PageResultDTO;
import org.zerock.natureRent.dto.ProductDTO;
import org.zerock.natureRent.dto.PageRequestDTO;
import org.zerock.natureRent.entity.Blog;
import org.zerock.natureRent.entity.Product;
import org.zerock.natureRent.service.ProductService;
import org.zerock.natureRent.service.RentalService;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/product")
@Log4j2
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService; //final
    private final RentalService rentalService;

    @Autowired
    public ProductController(ProductService productService, RentalService rentalService) {
        this.productService = productService;
        this.rentalService = rentalService;
    }

    @GetMapping("/register")
    public void register(){

    }
    @GetMapping("/test")
    public void test(){

    }

//    @GetMapping("/registerOld")
//    public void registerOld(){
//
//    }
//    @PostMapping("/registerOld")
//    public String registerOld(@ModelAttribute ProductDTO productDTO, RedirectAttributes redirectAttributes){
//        log.info("productDTO: " + productDTO);
//
//        // Price 값이 제대로 설정되었는지 확인
//        if (productDTO.getPrice() == null) {
//            // 오류 처리 로직 추가
//        }
//
//        Long mno = productService.register(productDTO);
//
//        redirectAttributes.addFlashAttribute("msg", mno);
//
//        return "redirect:/product/registerOld";
//    }


    @PostMapping("/register")
    public String register(@ModelAttribute ProductDTO productDTO, RedirectAttributes redirectAttributes){
        log.info("productDTO: " + productDTO);

        // Price 값이 제대로 설정되었는지 확인
        if (productDTO.getPrice() == null) {
            // 오류 처리 로직 추가
        }

        Long mno = productService.register(productDTO);

        redirectAttributes.addFlashAttribute("msg", mno);

        return "redirect:/product/register";
    }

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){

        log.info("pageRequestDTO: " + pageRequestDTO);


        model.addAttribute("result", productService.getList(pageRequestDTO));

    }

    @GetMapping({"/readM", "/modify"})
    public void readM(long mno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model ){

        log.info("mno: " + mno);

        ProductDTO productDTO = productService.getProduct(mno);

        model.addAttribute("dto", productDTO);

    }

    /*============================================================================*/
    @GetMapping("/product-details")
    public void read(long mno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model ){

        log.info("mno: " + mno);

        // ProductDTO 가져오기
        ProductDTO productDTO = productService.getProduct(mno);
        log.info("Fetched product DTO: " + productDTO);

        // RentalService를 이용하여 해당 상품의 렌탈 불가능한 날짜 가져오기
        List<LocalDateTime> rentedDates = rentalService.getRentedDatesByProductId(mno);

        // rentedDates가 null일 경우 빈 리스트로 초기화
        if (rentedDates == null) {
            rentedDates = Collections.emptyList();
        } else {
            rentedDates = rentedDates.stream()
                    .filter(Objects::nonNull)  // null 값을 필터링
                    .collect(Collectors.toList());
        }


        log.info("Fetched rented dates: " + rentedDates);

        //rentedDates를 뷰에 전달
        model.addAttribute("dto", productDTO);
        model.addAttribute("rentedDates", rentedDates
                .stream()
                .map(LocalDateTime::toString)
                .collect(Collectors.toList()));
//        model.addAttribute("dto", productDTO);
//        model.addAttribute("rentedDates", rentedDates);

//    log.info("exProductDetails..........");
//    if (mno == null) {
//        log.warn("mno parameter is missing");
//        return "redirect:/error"; // 매개변수가 없을 때 예외 처리
//    }
//
//    ProductDTO productDTO = productService.getProduct(mno);
//    if (productDTO == null) {
//        log.warn("Product not found with mno: " + mno);
//        return "redirect:/error"; // 예외 처리
//    }
//    log.info("ProductDTO: " + productDTO);
//    model.addAttribute("dto", productDTO);
//    return "main/product-details";

    }

    @GetMapping("/product-list")
    public String ProductList(PageRequestDTO pageRequestDTO, Model model) {

        log.info("pageRequestDTO: " + pageRequestDTO);


        model.addAttribute("result", productService.getList(pageRequestDTO));


        return "/product/product-list"; // 명시적으로 뷰 이름을 반환
    }

    @GetMapping("/product-grids")
    public String ProductGrids(PageRequestDTO pageRequestDTO, Model model) {

        log.info("pageRequestDTO: " + pageRequestDTO);

        // 페이지 사이즈를 9로 설정
        pageRequestDTO.setSize(9);

        // Pageable 객체를 PageRequestDTO에서 가져옴
//        Pageable pageable = pageRequestDTO.getPageable(Sort.by("mno").descending());

//        // PageResultDTO로 Product 엔티티를 가져오는 서비스 메서드 호출
//        PageResultDTO<ProductDTO, Object[]> result = productService.getList(pageRequestDTO);

//        // Page 객체로 Blog 엔티티를 가져오는 서비스 메서드 호출
//        Page<Blog> result = productService.findAllBlogs(pageable); // pageable을 사용하여 페이징 처리


        PageResultDTO<ProductDTO, Object[]> result = productService.getList(pageRequestDTO);


        model.addAttribute("result", productService.getList(pageRequestDTO));


        return "/product/product-grids"; // 명시적으로 뷰 이름을 반환
    }

}
