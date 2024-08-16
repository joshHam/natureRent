package org.zerock.natureRent.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.natureRent.entity.Blog;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog, Long> {
    // 추가적인 메소드 정의 가능


    Page<Blog> findAll(Pageable pageable);  // Pageable을 인자로 받아 페이징된 결과를 반환

//    방법 1: @Query 사용하기
    @Query("SELECT b, i FROM Blog b LEFT JOIN ProductImage i ON b.bno = i.blog.bno WHERE b.bno = :bno")
    List<Object[]> getBlogWithAll(@Param("bno") Long bno);

//    //방법 2: 메서드 이름 변경
//    List<Blog> findByBnoWithProductImages(Long bno);
}
