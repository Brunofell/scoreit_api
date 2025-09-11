package com.scoreit.scoreit.repository;

import com.scoreit.scoreit.dto.review.MediaType;
import com.scoreit.scoreit.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByMember_Id(Long memberId);

    List<Review> findByMediaId(String mediaId);

    List<Review> findByMember_IdIn(List<Long> memberIds);

    long countByMember_IdAndMediaType(Long memberId, MediaType mediaType);

    @Query("SELECT AVG(r.score) FROM Review r WHERE r.mediaId = :mediaId")
    Double findAverageScore(@Param("mediaId") String mediaId);

    @Query("SELECT r FROM Review r WHERE r.member.id = :memberId AND r.mediaType = :mediaType ORDER BY r.score DESC")
    List<Review> findTopByMemberAndMediaType(@Param("memberId") Long memberId,
                                             @Param("mediaType") MediaType mediaType,
                                             Pageable pageable);
}
