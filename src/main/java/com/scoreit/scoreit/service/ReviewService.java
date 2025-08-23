package com.scoreit.scoreit.service;

import com.scoreit.scoreit.dto.review.ReviewRegister;
import com.scoreit.scoreit.dto.review.ReviewResponse;
import com.scoreit.scoreit.dto.review.ReviewUpdate;
import com.scoreit.scoreit.entity.Member;
import com.scoreit.scoreit.entity.Review;
import com.scoreit.scoreit.repository.MemberFollowerRepository;
import com.scoreit.scoreit.repository.MemberRepository;
import com.scoreit.scoreit.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MemberRepository memberRepository;  // necessário para buscar Member

    @Autowired
    private MemberFollowerRepository memberFollowerRepository;

    @Autowired
    private AchievementService achievementService;

    public void reviewRegister(ReviewRegister dados) {
        Member member = memberRepository.findById(dados.memberId())
                .orElseThrow(() -> new IllegalArgumentException("Member not found with id: " + dados.memberId()));

        Review review = new Review(dados, member);
        reviewRepository.save(review);

        // conta quantas reviews desse tipo de mídia o usuário já fez
        long totalReviews = reviewRepository.countByMemberIdAndMediaType(member.getId(), "MOVIE");
        long totalReviewsSeries = reviewRepository.countByMemberIdAndMediaType(member.getId(), "SERIES");
        long totalReviewsAlbums = reviewRepository.countByMemberIdAndMediaType(member.getId(), "ALBUM");

        // checa conquistas de filmes
        achievementService.checkReviewAchievements(member, totalReviews, "MOVIE");
        achievementService.checkReviewAchievements(member, totalReviewsSeries, "SERIES");
        achievementService.checkReviewAchievements(member, totalReviewsAlbums, "ALBUM");
    }
    public void reviewUpdate(ReviewUpdate data){
        var review = reviewRepository.getReferenceById(data.id());
        review.updateInfos(data);
        reviewRepository.save(review);
    }

    public void deleteReview(Long id){
        reviewRepository.deleteById(id);
    }

    public List<ReviewResponse> getAllReviews(){
        return reviewRepository.findAll().stream().map(this::toResponse).toList();
    }

    public List<ReviewResponse> getReviewMemberById(Long memberId){
        return reviewRepository.findByMemberId(memberId).stream().map(this::toResponse).toList();
    }

    public List<ReviewResponse> getReviewMediaById(String mediaId){
        return reviewRepository.findByMediaId(mediaId).stream().map(this::toResponse).toList();
    }

    public List<ReviewResponse> getReviewsFromFollowedMembers(Long currentMemberId) {
        List<Long> followedIds = memberFollowerRepository.findFollowedIdsByFollowerId(currentMemberId);
        return reviewRepository.findByMemberIdIn(followedIds).stream().map(this::toResponse).toList();
    }

    // Conversor para DTO
    private ReviewResponse toResponse(Review review) {
        return new ReviewResponse(
                review.getId(),
                review.getMediaId(),
                review.getMediaType(),
                review.getMember().getId(),
                review.getScore(),
                review.getMemberReview(),
                review.getWatchDate(),
                review.isSpoiler(),
                review.getReviewDate()
        );
    }

    public Double findAverageScore(String mediaId){
        Double avg = reviewRepository.findAverageScore(mediaId);
        return avg != null ? avg : 0.0;
    }
}
