package org.zerock.natureRent.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.zerock.natureRent.entity.Blog;
import org.zerock.natureRent.entity.MemberOriginal;
import org.zerock.natureRent.entity.Product;
import org.zerock.natureRent.entity.Review;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @EntityGraph(attributePaths = {"member"}, type = EntityGraph.EntityGraphType.FETCH)
    List<Review> findByProduct(Product product);
    List<Review> findByBlog(Blog blog);

    @Modifying
    @Query("delete from Review mr where mr.member = :member")
    void deleteByMember(MemberOriginal member);

    List<Review> findByBlogBno(Long bno);
}
