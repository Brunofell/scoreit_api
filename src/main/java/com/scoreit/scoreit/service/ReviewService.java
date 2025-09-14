package com.scoreit.scoreit.service;

import com.scoreit.scoreit.dto.review.MediaType;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberFollowerRepository memberFollowerRepository;

    @Autowired
    private AchievementService achievementService;

    @Transactional
    public void reviewRegister(ReviewRegister dados) {
        // LOGS essenciais de depuração (não logar dados sensíveis)
        System.out.println("[reviewRegister] mediaId=" + dados.mediaId()
                + " mediaType=" + (dados.mediaType() != null ? dados.mediaType().name() : "null")
                + " memberId=" + dados.memberId()
                + " score=" + dados.score()
                + " watchDate=" + dados.watchDate()
                + " spoiler=" + dados.spoiler());

        // valida membro
        Member member = memberRepository.findById(dados.memberId())
                .orElseThrow(() -> new IllegalArgumentException("Member not found with id: " + dados.memberId()));

        // cria e salva review
        Review review = new Review(dados, member);
        reviewRepository.save(review);

        // contabilização retroativa por tipo (após salvar já contempla esta review)
        long totalReviewsMovies  = reviewRepository.countByMember_IdAndMediaType(member.getId(), MediaType.MOVIE);
        long totalReviewsSeries  = reviewRepository.countByMember_IdAndMediaType(member.getId(), MediaType.SERIES);
        long totalReviewsAlbums  = reviewRepository.countByMember_IdAndMediaType(member.getId(), MediaType.ALBUM);

        // concede conquistas conforme os totais atuais (>= limiar, implementado no AchievementService)
        achievementService.checkReviewAchievements(member, totalReviewsMovies,  "MOVIE");
        achievementService.checkReviewAchievements(member, totalReviewsSeries, "SERIES");
        achievementService.checkReviewAchievements(member, totalReviewsAlbums, "ALBUM");
    }

    @Transactional
    public void reviewUpdate(ReviewUpdate data){
        var review = reviewRepository.getReferenceById(data.id());
        review.updateInfos(data);
        reviewRepository.save(review);
        // não revoga/concede conquistas no update
    }

    @Transactional
    public void deleteReview(Long id){
        reviewRepository.deleteById(id);
        // decisão atual: não revogar conquistas ao deletar
    }

    public List<ReviewResponse> getAllReviews(){
        return reviewRepository.findAll().stream().map(this::toResponse).toList();
    }

    public List<ReviewResponse> getReviewMemberById(Long memberId){
        return reviewRepository.findByMember_Id(memberId).stream().map(this::toResponse).toList();
    }

    public List<ReviewResponse> getReviewMediaById(String mediaId){
        return reviewRepository.findByMediaId(mediaId).stream().map(this::toResponse).toList();
    }

    public List<ReviewResponse> getReviewsFromFollowedMembers(Long currentMemberId) {
        List<Long> followedIds = memberFollowerRepository.findFollowedIdsByFollowerId(currentMemberId);
        if (followedIds == null || followedIds.isEmpty()) {
            return Collections.emptyList();
        }
        return reviewRepository.findByMember_IdIn(followedIds).stream().map(this::toResponse).toList();
    }

    private ReviewResponse toResponse(Review review) {
        String mt = review.getMediaType() == null ? null : review.getMediaType().toFrontend();
        return new ReviewResponse(
                review.getId(),
                review.getMediaId(),
                mt,
                review.getMember().getId(),
                review.getScore(),
                review.getMemberReview(),
                review.getWatchDate(),
                review.isSpoiler(),
                review.getReviewDate()
        );
    }

    public Double findAverageScore(String mediaId, MediaType mediaType){
        Double avg = reviewRepository.findAverageScore(mediaId, mediaType);
        return avg != null ? avg : 0.0;
    }

}
