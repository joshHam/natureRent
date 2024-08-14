
package org.zerock.natureRent.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.natureRent.entity.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {


//    @Query("select m, avg(coalesce(r.grade,0)),  count(r) from Product m " +
//            "left outer join Review  r on r.product = m group by m")
//    Page<Object[]> getListPage(Pageable pageable);

    @Query("select m, mi, avg(coalesce(r.grade,0)),  count(r) from Product m " +
            "left outer join ProductImage mi on mi.product = m " +
            "left outer join Review  r on r.product = m group by m ")
    Page<Object[]> getListPage(Pageable pageable);


    @Query("select m, mi ,avg(coalesce(r.grade,0)),  count(r)" +
            " from Product m left outer join ProductImage mi on mi.product = m " +
            " left outer join Review  r on r.product = m "+
            " where m.mno = :mno group by mi")
    List<Object[]> getProductWithAll(Long mno);

//    @Query("SELECT p.rentalStartDate, p.rentalEndDate FROM Product p WHERE p.mno = :mno")
//    List<Object[]> findRentalPeriodsByProductId(@Param("mno") Long mno);

}
