package org.zerock.natureRent.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.zerock.natureRent.dto.RentalDTO;
import org.zerock.natureRent.entity.Product;
import org.zerock.natureRent.entity.Rental;
import org.zerock.natureRent.repository.ProductRepository;
import org.zerock.natureRent.repository.RentalRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RentalServiceImpl implements RentalService {

    private final RentalRepository rentalRepository;
    private final ProductRepository productRepository;

    @Override
    public Long registerRental(RentalDTO rentalDTO) {
        Rental rental = dtoToEntity(rentalDTO);
        Product product = productRepository.findById(rentalDTO.getMno()).orElseThrow(() -> new IllegalArgumentException("Invalid product ID"));
        rental.setProduct(product);
        rentalRepository.save(rental);
        return rental.getRno();
    }

    @Override
    public List<RentalDTO> getRentalsByProductId(Long mno) {
        List<Rental> rentals = rentalRepository.findByProductMno(mno);
        return rentals.stream().map(this::entityToDTO).collect(Collectors.toList());
    }
}
