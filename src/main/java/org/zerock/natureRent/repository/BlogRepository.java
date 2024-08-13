package org.zerock.natureRent.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.natureRent.entity.Blog;

public interface BlogRepository extends JpaRepository<Blog, Long> {
    // 추가적인 메소드 정의 가능


    Page<Blog> findAll(Pageable pageable);  // Pageable을 인자로 받아 페이징된 결과를 반환
}
