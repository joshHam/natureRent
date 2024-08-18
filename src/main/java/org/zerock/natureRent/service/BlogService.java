package org.zerock.natureRent.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zerock.natureRent.dto.BlogDTO;
import org.zerock.natureRent.dto.ProductImageDTO;
import org.zerock.natureRent.entity.Blog;
import org.zerock.natureRent.security.dto.MemberDTO;

import java.util.List;

//BlogService는 인터페이스야. 여기서는 블로그와 관련된 주요 기능(메서드)들을 정의해 놓는 거지.
// 예를 들면, 블로그를 저장하거나, 불러오는 등의 메서드를 정의해.
// 하지만, 실제로 메서드들이 어떻게 구현되는지는 정의하지 않아. 그냥 "이런 기능을 해야 한다"는 약속만 해놓는 거야.

public interface BlogService {
    void saveBlog(Blog blog, MemberDTO memberDTO, List<ProductImageDTO> imageDTOList);
    List<Blog> findAllBlogs();
    Blog findBlogById(Long id);
    BlogDTO getBlogWithImages(Long bno) ;
    Page<Blog> findAllBlogs(Pageable pageable);  // Pageable을 인자로 받아 페이징된 결과를 반환
    Page<BlogDTO> getBlogListWithImages(Pageable pageable);

}
