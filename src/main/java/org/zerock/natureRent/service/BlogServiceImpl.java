package org.zerock.natureRent.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.zerock.natureRent.dto.BlogDTO;
import org.zerock.natureRent.dto.ProductImageDTO;
import org.zerock.natureRent.entity.Blog;
import org.zerock.natureRent.entity.Member;
import org.zerock.natureRent.entity.ProductImage;
import org.zerock.natureRent.repository.BlogRepository;
import org.zerock.natureRent.repository.ProductImageRepository;
import org.zerock.natureRent.security.dto.MemberDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {
    private static final Logger logger = LoggerFactory.getLogger(BlogServiceImpl.class);

    private final BlogRepository blogRepository;
    private final ProductImageRepository imageRepository; // 이미지 저장을 위한 리포지토리

    @Override
    public void saveBlog(Blog blog, MemberDTO memberDTO, List<ProductImageDTO> imageDTOList) {
        blog.setMember(Member.builder().email(memberDTO.getEmail()).build());
        // 블로그를 먼저 저장
        blogRepository.save(blog);

        // 이미지 저장
        if (imageDTOList != null && !imageDTOList.isEmpty()) {
            imageDTOList.forEach(imageDTO -> {
                ProductImage productImage = ProductImage.builder()
                        .imgName(imageDTO.getImgName())
                        .path(imageDTO.getPath())
                        .uuid(imageDTO.getUuid())
                        .blog(blog)  // 블로그와 연관 설정
                        .build();
                imageRepository.save(productImage);
            });
        }
    }

    @Override
    public List<Blog> findAllBlogs() {
        return blogRepository.findAll();
    }

    @Override
    public Blog findBlogById(Long id) {
        return blogRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Blog> findAllBlogs(Pageable pageable) {
        return blogRepository.findAll(pageable);  // Repository 메서드를 호출하여 페이징된 결과를 반환
    }

    @Override
    public Page<BlogDTO> getBlogListWithImages(Pageable pageable) {
        return null;
    }

    // 새로운 메서드 추가
    public BlogDTO getBlogWithImages(Long bno) {
        // 블로그와 관련된 모든 데이터를 가져오기
        List<Object[]> result = blogRepository.getBlogWithAll(bno);

        if (result.isEmpty()) {
            // 결과가 없을 때 빈 BlogDTO 반환
            return BlogDTO.builder().build();
        }

        Blog blog = (Blog) result.get(0)[0];
        List<ProductImage> productImageList = new ArrayList<>();


        result.forEach(arr -> {
            ProductImage productImage = (ProductImage) arr[1];
            if (productImage != null) {
                logger.info("Found image: {}", productImage.getImgName());
                productImageList.add(productImage);
            } else {
                logger.warn("No image found for blog with id: {}", bno);
            }
        });



        return entitiesToDTO(blog, productImageList);
    }

    // Blog, ProductImage 엔티티들을 BlogDTO로 변환하는 메서드 (필요시 추가)
    private BlogDTO entitiesToDTO(Blog blog, List<ProductImage> productImageList) {
        List<ProductImageDTO> imageDTOList = productImageList.stream()
//                .map(productImage -> new ProductImageDTO(productImage.getImgName(), productImage.getUuid(), productImage.getPath()))
                .map(productImage -> ProductImageDTO.builder()
                        .imgName(productImage.getImgName())
                        .uuid(productImage.getUuid())
                        .path(productImage.getPath())
                        .build())
                .collect(Collectors.toList());

        return BlogDTO.builder()
                .bno(blog.getBno())
                .title(blog.getTitle())
                .detail(blog.getDetail())
                .member_email(blog.getMember().getEmail())
                .regDate(blog.getRegDate())  // regDate 설정
                .modDate(blog.getModDate())  // modDate 설정
                .imageDTOList(imageDTOList)
                .build();
    }
}
