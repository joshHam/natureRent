package org.zerock.natureRent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.natureRent.entity.ProductImage;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
}
