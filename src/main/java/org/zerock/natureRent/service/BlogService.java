package org.zerock.natureRent.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zerock.natureRent.entity.Blog;
import org.zerock.natureRent.security.dto.MemberDTO;

import java.util.List;

public interface BlogService {
    void saveBlog(Blog blog, MemberDTO memberDTO);
    List<Blog> findAllBlogs();
    Blog findBlogById(Long id);

    Page<Blog> findAllBlogs(Pageable pageable);  // Pageable을 인자로 받아 페이징된 결과를 반환

}
