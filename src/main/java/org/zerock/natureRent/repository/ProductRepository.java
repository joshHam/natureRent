
package org.zerock.natureRent.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.natureRent.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {


//    @Query("select m, avg(coalesce(r.grade,0)),  count(r) from Product m " +
//            "left outer join Review  r on r.product = m group by m")
//    Page<Object[]> getListPage(Pageable pageable);

//    @Query("select m, mi, avg(coalesce(r.grade,0)),  count(r) from Product m " +
//            "left outer join ProductImage mi on mi.product = m " +
//            "left outer join Review  r on r.product = m group by m ")
//    Page<Object[]> getListPage(Pageable pageable);
@Query("select m, mi, avg(coalesce(r.grade,0)), count(DISTINCT r) " +
        "from Product m " +
        "left outer join ProductImage mi on mi.product = m " +
        "left outer join Review r on r.product = m " +
        "group by m ")
Page<Object[]> getListPage(Pageable pageable);






//    @Query("select m, mi ,avg(coalesce(r.grade,0)),  count(r)" +
//            " from Product m left outer join ProductImage mi on mi.product = m " +
//            " left outer join Review  r on r.product = m "+
//            " where m.mno = :mno group by mi")
//    List<Object[]> getProductWithAll(Long mno);
/*
@Query("select m, mi, avg(coalesce(r.grade,0)), count(DISTINCT r) " +
        "from Product m " +
        "left outer join ProductImage mi on mi.product = m " +
        "left outer join Review r on r.product = m " +
        "where m.mno = :mno " +
        "group by m")
List<Object[]> getProductWithAll(@Param("mno") Long mno);
*/
@Query("select m, mi, avg(coalesce(r.grade,0)), count(DISTINCT r) " +
        "from Product m " +
        "left outer join ProductImage mi on mi.product = m " +
        "left outer join Review r on r.product = m " +
        "where m.mno = :mno " +
        "group by m, mi")
List<Object[]> getProductWithAll(@Param("mno") Long mno);







//    @Query("select m, mi, avg(coalesce(r.grade,0)), count(r) " +
//            "from Product m " +
//            "left join ProductImage mi on mi.product = m " +
//            "left join Review r on r.product = m " +
//            "where m.mno = :mno")
//    List<Object[]> getProductWithAll(@Param("mno") Long mno);

    @SuppressWarnings("unused")
    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.imageList WHERE p.mno = :mno")
    Optional<Product> findByIdWithImages(@Param("mno") Long mno);

    // 제목이나 상세 설명에 검색어가 포함된 상품을 검색하는 쿼리
    Page<Product> findByTitleContainingOrDetailContaining(String title, String detail, Pageable pageable);


}
