package com.scoreit.scoreit.repository;

import com.scoreit.scoreit.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByMemberId(String memberId);
    List<Review> findByMediaId(String mediaId);

}
