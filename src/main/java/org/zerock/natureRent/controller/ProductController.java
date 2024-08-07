package org.zerock.natureRent.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.natureRent.dto.ProductDTO;
import org.zerock.natureRent.dto.PageRequestDTO;
import org.zerock.natureRent.service.ProductService;

@Controller
@RequestMapping("/product")
@Log4j2
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService; //final

    @GetMapping("/register")
    public void register(){

    }
    @GetMapping("/test")
    public void test(){

    }


    @PostMapping("/register")
    public String register(ProductDTO productDTO, RedirectAttributes redirectAttributes){
        log.info("productDTO: " + productDTO);

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

        ProductDTO productDTO = productService.getProduct(mno);

        model.addAttribute("dto", productDTO);

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


        model.addAttribute("result", productService.getList(pageRequestDTO));


        return "/product/product-grids"; // 명시적으로 뷰 이름을 반환
    }

}
