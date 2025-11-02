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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private MemberFollowerRepository memberFollowerRepository;

    @Mock
    private AchievementService achievementService;

    @InjectMocks
    private ReviewService reviewService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // =========================
    // Teste do reviewRegister
    // =========================
    @Test
    void testReviewRegisterSuccess() {
        Member member = new Member();
        member.setId(1L);

        ReviewRegister register = mock(ReviewRegister.class);
        when(register.memberId()).thenReturn(1L);
        when(register.mediaId()).thenReturn("abc");
        when(register.mediaType()).thenReturn(MediaType.MOVIE);
        when(register.score()).thenReturn(8);
        when(register.watchDate()).thenReturn(LocalDate.now());
        when(register.spoiler()).thenReturn(false);

        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));
        when(reviewRepository.save(any(Review.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(reviewRepository.countByMember_IdAndMediaType(1L, MediaType.MOVIE)).thenReturn(1L);
        when(reviewRepository.countByMember_IdAndMediaType(1L, MediaType.SERIES)).thenReturn(0L);
        when(reviewRepository.countByMember_IdAndMediaType(1L, MediaType.ALBUM)).thenReturn(0L);

        reviewService.reviewRegister(register);

        verify(reviewRepository).save(any(Review.class));
        verify(achievementService).checkReviewAchievements(member, 1L, "MOVIE");
        verify(achievementService).checkReviewAchievements(member, 0L, "SERIES");
        verify(achievementService).checkReviewAchievements(member, 0L, "ALBUM");
    }

    @Test
    void testReviewRegisterMemberNotFound() {
        ReviewRegister register = mock(ReviewRegister.class);
        when(register.memberId()).thenReturn(1L);

        when(memberRepository.findById(1L)).thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> reviewService.reviewRegister(register));
        assertEquals("Member not found with id: 1", ex.getMessage());
    }

    // =========================
    // Teste do reviewUpdate
    // =========================
    @Test
    void testReviewUpdate() {
        ReviewUpdate update = mock(ReviewUpdate.class);
        when(update.id()).thenReturn(1L);

        Review review = mock(Review.class);
        when(reviewRepository.getReferenceById(1L)).thenReturn(review);

        reviewService.reviewUpdate(update);

        verify(review).updateInfos(update);
        verify(reviewRepository).save(review);
    }

    // =========================
    // Teste do deleteReview
    // =========================
    @Test
    void testDeleteReview() {
        doNothing().when(reviewRepository).deleteById(1L);

        reviewService.deleteReview(1L);

        verify(reviewRepository).deleteById(1L);
    }

    // =========================
    // Teste do getAllReviews
    // =========================
    @Test
    void testGetAllReviews() {
        Review review = mock(Review.class);
        when(review.getId()).thenReturn(1L);
        when(review.getMediaId()).thenReturn("abc");
        when(review.getMediaType()).thenReturn(MediaType.MOVIE);
        Member member = new Member();
        member.setId(1L);
        when(review.getMember()).thenReturn(member);
        when(review.getScore()).thenReturn(9);
        when(review.getMemberReview()).thenReturn("Good");
        when(review.getWatchDate()).thenReturn(LocalDate.now());
        when(review.isSpoiler()).thenReturn(false);
        when(review.getReviewDate()).thenReturn(LocalDate.now().atStartOfDay());

        when(reviewRepository.findAll()).thenReturn(List.of(review));

        List<ReviewResponse> result = reviewService.getAllReviews();

        assertEquals(1, result.size());
        assertEquals("abc", result.get(0).mediaId());
    }

    // =========================
    // Teste do getReviewMemberById
    // =========================
    @Test
    void testGetReviewMemberById() {
        Review review = mock(Review.class);
        Member member = new Member();
        member.setId(1L);
        when(review.getMember()).thenReturn(member);
        when(reviewRepository.findByMember_Id(1L)).thenReturn(List.of(review));

        List<ReviewResponse> reviews = reviewService.getReviewMemberById(1L);

        assertEquals(1, reviews.size());
        verify(reviewRepository).findByMember_Id(1L);
    }

    // =========================
    // Teste do getReviewMediaById
    // =========================
//    @Test
//    void testGetReviewMediaById() {
//        Review review = mock(Review.class);
//        when(review.getMediaId()).thenReturn("media1");
//        when(reviewRepository.findByMediaId("media1")).thenReturn(List.of(review));
//
//        List<ReviewResponse> reviews = reviewService.getReviewMediaById("media1");
//
//        assertEquals(1, reviews.size());
//        verify(reviewRepository).findByMediaId("media1");
//    }

    // =========================
    // Teste do getReviewsFromFollowedMembers
    // =========================
    @Test
    void testGetReviewsFromFollowedMembersEmpty() {
        when(memberFollowerRepository.findFollowedIdsByFollowerId(1L)).thenReturn(Collections.emptyList());

        List<ReviewResponse> reviews = reviewService.getReviewsFromFollowedMembers(1L);

        assertTrue(reviews.isEmpty());
    }

    @Test
    void testGetReviewsFromFollowedMembersWithData() {
        when(memberFollowerRepository.findFollowedIdsByFollowerId(1L)).thenReturn(List.of(2L));
        Review review = mock(Review.class);
        Member member = new Member();
        member.setId(2L);
        when(review.getMember()).thenReturn(member);
        when(reviewRepository.findByMember_IdIn(List.of(2L))).thenReturn(List.of(review));

        List<ReviewResponse> reviews = reviewService.getReviewsFromFollowedMembers(1L);

        assertEquals(1, reviews.size());
        verify(reviewRepository).findByMember_IdIn(List.of(2L));
    }

    // =========================
    // Teste do findAverageScore
    // =========================
    @Test
    void testFindAverageScoreNonNull() {
        when(reviewRepository.findAverageScore("media1", MediaType.MOVIE)).thenReturn(8.5);

        Double avg = reviewService.findAverageScore("media1", MediaType.MOVIE);

        assertEquals(8.5, avg);
    }

    @Test
    void testFindAverageScoreNull() {
        when(reviewRepository.findAverageScore("media1", MediaType.MOVIE)).thenReturn(null);

        Double avg = reviewService.findAverageScore("media1", MediaType.MOVIE);

        assertEquals(0.0, avg);
    }
}
