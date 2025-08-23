package com.scoreit.scoreit.repository;

import com.scoreit.scoreit.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    // Buscar reviews pelo id do membro (que agora é Long)
    List<Review> findByMemberId(Long memberId);

    List<Review> findByMediaId(String mediaId);

    List<Review> findByMemberIdIn(List<Long> memberIds);

    long countByMemberIdAndMediaType(Long memberId, String mediaType);
}
