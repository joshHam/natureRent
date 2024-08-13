package org.zerock.natureRent.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.natureRent.entity.Blog;
import org.zerock.natureRent.entity.Member;
import org.zerock.natureRent.repository.BlogRepository;
import org.zerock.natureRent.repository.MemberRepository;

import java.util.stream.IntStream;

@SpringBootTest
public class BlogRepositoryTests {

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Commit
    @Transactional
    @Test
    public void insertBlog() {


        IntStream.rangeClosed(1, 20).forEach(i -> {
        // 작성할 멤버 조회 (여기서는 이메일을 통해 조회한다고 가정)
        String memberEmail = "2@2.com";
        Member member = memberRepository.findById(memberEmail)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        // 블로그 글 생성
        Blog blog = Blog.builder()
                .title("Sample Blog" + i)
                .detail("This is a sample blog detail.")
                .likes(0)
                .views(0)
                .tags("sample, blog, test")
                .member(member)
                .build();

        // 블로그 저장
        blogRepository.save(blog);

        System.out.println("Blog Created: " + blog);
        });
    }
}
