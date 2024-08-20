//default ProductDTO entitiesToDTO(Product product, List<ProductImage> productImages, Double avg, Long reviewCnt) {
//    log.info("Converting entities to DTO for product: {}", product.getMno());
//
//    ProductDTO productDTO = ProductDTO.builder()
//            .mno(product.getMno())
//            .title(product.getTitle())
//            .regDate(product.getRegDate())
//            .modDate(product.getModDate())
//            .avg((avg != null) ? avg : 0.0)
//            .isAvailable(product.isAvailable())
//            .reviewCnt((reviewCnt != null) ? reviewCnt.intValue() : 0)
//            .price(product.getPrice())
//            .build();
//
//    List<ProductImageDTO> productImageDTOList = productImages.stream().map(productImage -> {
//        log.info("Converting ProductImage to ProductImageDTO: UUID = {}, Path = {}", productImage.getUuid(), productImage.getPath());
//        return ProductImageDTO.builder()
//                .imgName(productImage.getImgName())
//                .path(productImage.getPath())
//                .uuid(productImage.getUuid())
//                .build();
//    }).collect(Collectors.toList());
//
//    productDTO.setImageDTOList(productImageDTOList);
//
//    // 최종적으로 DTO에 이미지가 잘 설정되었는지 확인
//    if (productDTO.getImageDTOList().isEmpty()) {
//        log.warn("ImageDTOList is empty after conversion for product: {}", product.getMno());
//    } else {
//        log.info("ImageDTOList size after conversion: {}", productDTO.getImageDTOList().size());
//    }
//
//    return productDTO;
//}
