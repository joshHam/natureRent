//package org.zerock.natureRent.service;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.zerock.natureRent.entity.Blog;
//import org.zerock.natureRent.entity.Member;
//import org.zerock.natureRent.repository.BlogRepository;
//import org.zerock.natureRent.security.dto.MemberDTO;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class BlogService2 {
//
//    private final BlogRepository blogRepository;
//
//    public void saveBlog(Blog blog, MemberDTO memberDTO) {
//        blog.setMember(Member.builder().email(memberDTO.getEmail()).build());
//        blogRepository.save(blog);
//    }
//
//    public List<Blog> findAllBlogs() {
//        return blogRepository.findAll();
//    }
//
//    public Blog findBlogById(Long id) {
//        return blogRepository.findById(id).orElse(null);
//    }
//}
