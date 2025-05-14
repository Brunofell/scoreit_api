package com.scoreit.scoreit.repository;

import com.scoreit.scoreit.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
