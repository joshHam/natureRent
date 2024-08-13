package org.zerock.natureRent.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.zerock.natureRent.entity.Blog;
import org.zerock.natureRent.entity.Member;
import org.zerock.natureRent.repository.BlogRepository;
import org.zerock.natureRent.security.dto.MemberDTO;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {

    private final BlogRepository blogRepository;

    @Override
    public void saveBlog(Blog blog, MemberDTO memberDTO) {
        blog.setMember(Member.builder().email(memberDTO.getEmail()).build());
        blogRepository.save(blog);
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
}
