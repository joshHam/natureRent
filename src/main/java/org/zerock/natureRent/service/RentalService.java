package org.zerock.natureRent.service;

import org.zerock.natureRent.dto.RentalSaveDTO;
import org.zerock.natureRent.entity.Rental;

import java.time.LocalDateTime;
import java.util.List;

public interface RentalService {


    Long registerRental(RentalSaveDTO rentalSaveDTO);

//    List<RentalDTO> getRentalsByProductId(Long mno);
    List<LocalDateTime> getRentedDatesByProductId(Long mno);

    default RentalSaveDTO entityToDTO(Rental rental){
        return RentalSaveDTO.builder()
//                .rentalId(rental.getRentalId())
//                .productMno(rental.getProduct().getMno())
                .rno(rental.getRno())
                .mno(rental.getProduct().getMno())
                .rentalStartDate(rental.getRentalStartDate())
                .rentalEndDate(rental.getRentalEndDate())
                .quantity(rental.getQuantity())
                .build();
    }

    default Rental dtoToEntity(RentalSaveDTO rentalSaveDTO){
        return Rental.builder()
                .rno(rentalSaveDTO.getRno())
                .rentalStartDate(rentalSaveDTO.getRentalStartDate())
                .rentalEndDate(rentalSaveDTO.getRentalEndDate())
                .quantity(rentalSaveDTO.getQuantity())
                .build();
    }
}
