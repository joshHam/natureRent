package org.zerock.natureRent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.natureRent.entity.MovieImage;

public interface MovieImageRepository extends JpaRepository<MovieImage, Long> {
}
