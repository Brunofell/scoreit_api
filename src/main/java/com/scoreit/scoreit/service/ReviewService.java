package com.scoreit.scoreit.service;

import com.scoreit.scoreit.api.tmdb.movie.service.MovieService;
import com.scoreit.scoreit.api.tmdb.series.service.SeriesService;
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
import java.util.stream.Collectors;

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
    @Autowired
    private MovieService movieService;

    @Autowired
    private SeriesService seriesService;

    public void reviewRegister(ReviewRegister dados) {
        Member member = memberRepository.findById(dados.memberId())
                .orElseThrow(() -> new IllegalArgumentException("Member not found with id: " + dados.memberId()));

        String genres = null;

        if ("MOVIE".equalsIgnoreCase(dados.mediaType())) {
            var movie = movieService.getMovieDetails(Integer.parseInt(dados.mediaId()));
            if (movie.genres() != null) {
                genres = movie.genres().stream()
                        .map(g -> g.name())
                        .collect(Collectors.joining(","));
            }
        } else if ("SERIES".equalsIgnoreCase(dados.mediaType())) {
            var series = seriesService.getSeriesDetail(Integer.parseInt(dados.mediaId()));
            if (series.genres() != null) {
                genres = String.join(",", series.genres());
            }
        }
//        else if ("ALBUM".equalsIgnoreCase(dados.mediaType())) {
//            var album = albumService.getAlbumDetails(dados.mediaId());
//            if (album.genres() != null) {
//                genres = String.join(",", album.genres());
//            }
//        }

        Review review = new Review(dados, member, genres);
        reviewRepository.save(review);

        // mantém conquistas
        long totalReviews = reviewRepository.countByMemberIdAndMediaType(member.getId(), "MOVIE");
        long totalReviewsSeries = reviewRepository.countByMemberIdAndMediaType(member.getId(), "SERIES");
        long totalReviewsAlbums = reviewRepository.countByMemberIdAndMediaType(member.getId(), "ALBUM");

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
                review.getReviewDate(),
                review.getGenres()
        );
    }

    public Double findAverageScore(String mediaId){
        Double avg = reviewRepository.findAverageScore(mediaId);
        return avg != null ? avg : 0.0;
    }
}
