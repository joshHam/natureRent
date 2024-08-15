package org.zerock.natureRent.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.zerock.natureRent.dto.RentalDTO;
import org.zerock.natureRent.entity.Product;
import org.zerock.natureRent.entity.Rental;
import org.zerock.natureRent.repository.ProductRepository;
import org.zerock.natureRent.repository.RentalRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Log4j2  // log 변수를 자동으로 생성하는 애노테이션
public class RentalServiceImpl implements RentalService {

    private final RentalRepository rentalRepository;
    private final ProductRepository productRepository;

//    public RentalServiceImpl(RentalRepository rentalRepository, ProductRepository productRepository) {
//        this.rentalRepository = rentalRepository;
//        this.productRepository = productRepository;
//    }

    @Override
    public Long registerRental(RentalDTO rentalDTO) {
        Rental rental = dtoToEntity(rentalDTO);
        Product product = productRepository.findById(rentalDTO.getMno()).orElseThrow(() -> new IllegalArgumentException("Invalid product ID"));
        rental.setProduct(product);
        rentalRepository.save(rental);
        return rental.getRno();
    }

    @Override
    public List<LocalDateTime> getRentedDatesByProductId(Long mno) {
        log.info("Fetching rented dates for product: " + mno);

        List<Object[]> rentalPeriods = rentalRepository.findRentedDatesByProductId(mno);
        log.info("Fetched rental periods: " + rentalPeriods);

        // 각 기간의 시작일과 종료일을 하나의 리스트로 변환
        List<LocalDateTime> rentedDates = rentalPeriods.stream()
                .flatMap(period -> {
                    LocalDateTime start = (LocalDateTime) period[0];
                    LocalDateTime end = (LocalDateTime) period[1];
//                    return start.toLocalDate().datesUntil(end.toLocalDate().plusDays(1))
//                            .map(LocalDate::atStartOfDay);
                    return Stream.iterate(start, date -> date.plusDays(1))
                            .limit(start.until(end, java.time.temporal.ChronoUnit.DAYS) + 1);
                })
                .collect(Collectors.toList());
        log.info("Converted rented dates: " + rentedDates);

        return rentedDates;
    }


//    @Override
//    public List<RentalDTO> getRentalsByProductId(Long mno) {
//        List<Rental> rentals = rentalRepository.findByProductMno(mno);
//        return rentals.stream().map(this::entityToDTO).collect(Collectors.toList());
//    }
}
