package org.zerock.natureRent.service;

import org.zerock.natureRent.dto.RentalDTO;
import org.zerock.natureRent.entity.Rental;

import java.time.LocalDateTime;
import java.util.List;

public interface RentalService {


    Long registerRental(RentalDTO rentalDTO);

//    List<RentalDTO> getRentalsByProductId(Long mno);
    List<LocalDateTime> getRentedDatesByProductId(Long mno);

    default RentalDTO entityToDTO(Rental rental){
        return RentalDTO.builder()
                .rno(rental.getRno())
                .rno(rental.getProduct().getMno())
                .rentalStartDate(rental.getRentalStartDate())
                .rentalEndDate(rental.getRentalEndDate())
                .quantity(rental.getQuantity())
                .build();
    }

    default Rental dtoToEntity(RentalDTO rentalDTO){
        return Rental.builder()
                .rno(rentalDTO.getRno())
                .rentalStartDate(rentalDTO.getRentalStartDate())
                .rentalEndDate(rentalDTO.getRentalEndDate())
                .quantity(rentalDTO.getQuantity())
                .build();
    }
}
