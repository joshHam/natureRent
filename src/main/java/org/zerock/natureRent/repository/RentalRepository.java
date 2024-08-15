package org.zerock.natureRent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.zerock.natureRent.entity.Rental;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {

    // 특정 상품의 모든 예약을 가져오기
    List<Rental> findByProductMno(Long mno);

    // 특정 고객의 모든 예약을 가져오기
    List<Rental> findByCustomerEmail(String email);

    // 특정 상품의 특정 기간 동안의 예약을 가져오기
    List<Rental> findByProductMnoAndRentalStartDateLessThanEqualAndRentalEndDateGreaterThanEqual(
            Long mno, LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT r.rentalStartDate, r.rentalEndDate FROM Rental r WHERE r.product.mno = :mno")
    List<Object[]> findRentedDatesByProductId(@Param("mno") Long mno);

//    // 특정 상품의 렌탈 기간을 가져오는 쿼리
//    @Query("SELECT r.rentalStartDate FROM Rental r WHERE r.product.mno = :mno")
//    List<Object[]> findRentedDatesByProductId(@Param("mno") Long mno);
}
