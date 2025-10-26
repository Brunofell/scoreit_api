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

    @Query("SELECT AVG(r.score) FROM Review r WHERE r.mediaId = :mediaId AND r.mediaType = :mediaType")
    Double findAverageScore(@Param("mediaId") String mediaId, @Param("mediaType") MediaType mediaType);

    @Query("SELECT r FROM Review r WHERE r.member.id = :memberId AND r.mediaType = :mediaType ORDER BY r.score DESC")
    List<Review> findTopByMemberAndMediaType(@Param("memberId") Long memberId,
                                             @Param("mediaType") MediaType mediaType,
                                             Pageable pageable);

    // Contagem de reviews por data (últimos X dias, por exemplo)
    @Query("SELECT DATE(r.reviewDate) AS date, COUNT(r) AS total " +
            "FROM Review r " +
            "GROUP BY DATE(r.reviewDate) " +
            "ORDER BY date ASC")
    List<Object[]> countReviewsByDate();

    // Mídias mais populares (pelas reviews)
    @Query("SELECT r.mediaId, r.mediaType, COUNT(r) AS total " +
            "FROM Review r " +
            "GROUP BY r.mediaId, r.mediaType " +
            "ORDER BY total DESC")
    List<Object[]> findMostPopularMedia();
}
