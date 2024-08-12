//@Service
//@Log4j2
//@RequiredArgsConstructor
//public class ReviewServiceImpl implements ReviewService {
//
//    private final ReviewRepository reviewRepository;
//
//    @Override
//    public List<ReviewDTO> getListOfProduct(Long mno){
//        Product product = Product.builder().mno(mno).build();
//        List<Review> result = reviewRepository.findByProduct(product);
//        return result.stream().map(this::entityToDto).collect(Collectors.toList());
//    }
//
//    @Override
//    public Long register(ReviewDTO productReviewDTO) {
//        Review productReview = dtoToEntity(productReviewDTO);
//        reviewRepository.save(productReview);
//        return productReview.getReviewnum();
//    }
//
//    @Override
//    public void modify(ReviewDTO productReviewDTO) {
//        Optional<Review> result = reviewRepository.findById(productReviewDTO.getReviewnum());
//        result.ifPresent(productReview -> {
//            productReview.changeGrade(productReviewDTO.getGrade());
//            productReview.changeText(productReviewDTO.getText());
//            reviewRepository.save(productReview);
//        });
//    }
//
//    @Override
//    public void remove(Long reviewnum) {
//        reviewRepository.deleteById(reviewnum);
//    }
//
//    @Override
//    public List<ReviewDTO> getListOfBlog(Long bno) {
//        Blog blog = Blog.builder().bno(bno).build();
//        List<Review> result = reviewRepository.findByBlogBno(bno);
//        return result.stream().map(this::entityToDto).collect(Collectors.toList());
//    }
//
//    @Override
//    public Long registerBlogReview(ReviewDTO blogReviewDTO) {
//        Review blogReview = dtoToEntity(blogReviewDTO);
//        reviewRepository.save(blogReview);
//        return blogReview.getReviewnum();
//    }
//
//    @Override
//    public void modifyBlogReview(ReviewDTO blogReviewDTO) {
//        Optional<Review> result = reviewRepository.findById(blogReviewDTO.getReviewnum());
//        result.ifPresent(blogReview -> {
//            blogReview.changeGrade(blogReviewDTO.getGrade());
//            blogReview.changeText(blogReviewDTO.getText());
//            reviewRepository.save(blogReview);
//        });
//    }
//
//    @Override
//    public void removeBlogReview(Long reviewnum) {
//        reviewRepository.deleteById(reviewnum);
//    }
//}
//
