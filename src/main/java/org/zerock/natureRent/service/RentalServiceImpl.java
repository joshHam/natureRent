package org.zerock.natureRent.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.natureRent.dto.RentalSaveDTO;
import org.zerock.natureRent.entity.Member;
import org.zerock.natureRent.entity.Product;
import org.zerock.natureRent.entity.Rental;
import org.zerock.natureRent.repository.MemberRepository;
import org.zerock.natureRent.repository.ProductRepository;
import org.zerock.natureRent.repository.RentalRepository;

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
    private final MemberRepository memberRepository;

//    public RentalServiceImpl(RentalRepository rentalRepository, ProductRepository productRepository) {
//        this.rentalRepository = rentalRepository;
//        this.productRepository = productRepository;
//    }


    @Transactional
    @Override
    public Long registerRental(RentalSaveDTO rentalSaveDTO) {
        Rental rental = dtoToEntity(rentalSaveDTO);

        // 상품과 고객을 찾기 위해 리포지토리 사용
        Product product = productRepository.findById(rentalSaveDTO.getMno())
                .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다."));
        Member customer = memberRepository.findByEmail(rentalSaveDTO.getEmail(), false)
                .orElseThrow(() -> new RuntimeException("고객을 찾을 수 없습니다."));

        // Rental 엔티티에 설정
        rental.setProduct(product);
        rental.setCustomer(customer);

        // 기타 추가 필드 설정
        rental.setOrderPrice(rentalSaveDTO.getOrderPrice());
        rental.setReceiverName(rentalSaveDTO.getReceiverName());
        rental.setPhoneNumber(rentalSaveDTO.getPhoneNumber());
        rental.setZipcode(rentalSaveDTO.getZipcode());
        rental.setAddress(rentalSaveDTO.getAddress());
        rental.setOrderRequired(rentalSaveDTO.getOrderRequired());
        rental.setPaymentMethod(rentalSaveDTO.getPaymentMethod());

        rentalRepository.save(rental);
        log.info("렌탈이 등록되었습니다. 주문 번호: {}", rental.getRno());

        return rental.getRno();
    }










//    @Override
//    public Long registerRental(RentalSaveDTO rentalSaveDTO) {
//        Rental rental = dtoToEntity(rentalSaveDTO);
//        Product product = productRepository.findById(rentalSaveDTO.getMno()).orElseThrow(() -> new IllegalArgumentException("Invalid product ID"));
//        rental.setProduct(product);
//        rentalRepository.save(rental);
//        return rental.getRno();
//    }

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
                    return Stream.iterate(start, date -> date.plusDays(1))
                            .limit(start.until(end, java.time.temporal.ChronoUnit.DAYS) + 1);
                })
                .collect(Collectors.toList());
        log.info("Converted rented dates: " + rentedDates);
        return rentedDates;
    }


    @Override
    public RentalSaveDTO entityToDTO(Rental rental) {
        return RentalSaveDTO.builder()
                .rno(rental.getRno())
                .mno(rental.getProduct().getMno())
                .rentalStartDate(rental.getRentalStartDate())
                .rentalEndDate(rental.getRentalEndDate())
                .quantity(rental.getQuantity())
                .orderPrice(rental.getOrderPrice())
                .receiverName(rental.getReceiverName())
                .phoneNumber(rental.getPhoneNumber())
                .zipcode(rental.getZipcode())
                .address(rental.getAddress())
                .orderRequired(rental.getOrderRequired())
                .paymentMethod(rental.getPaymentMethod())
                .email(rental.getCustomer().getEmail())
                .build();
    }

    @Override
    public Rental dtoToEntity(RentalSaveDTO rentalSaveDTO) {
        return Rental.builder()
                .rno(rentalSaveDTO.getRno())
                .rentalStartDate(rentalSaveDTO.getRentalStartDate())
                .rentalEndDate(rentalSaveDTO.getRentalEndDate())
                .quantity(rentalSaveDTO.getQuantity())
                .orderPrice(rentalSaveDTO.getOrderPrice())
                .receiverName(rentalSaveDTO.getReceiverName())
                .phoneNumber(rentalSaveDTO.getPhoneNumber())
                .zipcode(rentalSaveDTO.getZipcode())
                .address(rentalSaveDTO.getAddress())
                .orderRequired(rentalSaveDTO.getOrderRequired())
                .paymentMethod(rentalSaveDTO.getPaymentMethod())
                .build();
    }



}
