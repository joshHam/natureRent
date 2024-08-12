//@Controller
//@RequestMapping("/reviews")
//@Log4j2
//@RequiredArgsConstructor
//public class ReviewController {
//
//    private final ReviewService reviewService;
//
//    @GetMapping("/{mno}/all")
//    public ResponseEntity<List<ReviewDTO>> getList(@PathVariable("mno") Long mno){
//        List<ReviewDTO> reviewDTOList = reviewService.getListOfProduct(mno);
//        return new ResponseEntity<>(reviewDTOList, HttpStatus.OK);
//    }
//
//    @PostMapping("/{mno}")
//    public ResponseEntity<Long> addReview(@RequestBody ReviewDTO productReviewDTO){
//        Long reviewnum = reviewService.register(productReviewDTO);
//        return new ResponseEntity<>( reviewnum, HttpStatus.OK);
//    }
//
//    @PutMapping("/{mno}/{reviewnum}")
//    public ResponseEntity<Long> modifyReview(@PathVariable Long reviewnum, @RequestBody ReviewDTO productReviewDTO){
//        reviewService.modify(productReviewDTO);
//        return new ResponseEntity<>( reviewnum, HttpStatus.OK);
//    }
//
//    @DeleteMapping("/{mno}/{reviewnum}")
//    public ResponseEntity<Long> removeReview( @PathVariable Long reviewnum){
//        reviewService.remove(reviewnum);
//        return new ResponseEntity<>( reviewnum, HttpStatus.OK);
//    }
//
//    @GetMapping("/blog/{blogId}")
//    public ResponseEntity<List<ReviewDTO>> getReviewsByBlogId(@PathVariable Long blogId) {
//        List<ReviewDTO> reviews = reviewService.getListOfBlog(blogId);
//        return new ResponseEntity<>(reviews, HttpStatus.OK);
//    }
//
//    @GetMapping("/product/{productId}")
//    public ResponseEntity<List<ReviewDTO>> getReviewsByProductId(@PathVariable Long productId) {
//        List<ReviewDTO> reviews = reviewService.getListOfProduct(productId);
//        return new ResponseEntity<>(reviews, HttpStatus.OK);
//    }
//
//    @PostMapping("/add")
//    public String addReview(
//            @RequestParam("name") String name,
//            @RequestParam("email") String email,
//            @RequestParam("text") String text,
//            @RequestParam("blogId") Long blogId,
//            RedirectAttributes redirectAttributes) {
//        ReviewDTO reviewDTO = new ReviewDTO();
//        reviewDTO.setName(name);
//        reviewDTO.setMember_email(email);
//        reviewDTO.setText(text);
//        reviewDTO.setBno(blogId); // 블로그 ID 설정
//        reviewService.register(reviewDTO);
//        redirectAttributes.addFlashAttribute("message", "Review added successfully!");
//        return "redirect:/blog/" + blogId;
//    }
//}
