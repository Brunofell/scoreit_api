package com.scoreit.scoreit.service;

import com.scoreit.scoreit.dto.review.MediaType;
import com.scoreit.scoreit.entity.Review;
import com.scoreit.scoreit.repository.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecommendationServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private RecommendationService recommendationService;

    private Review review1;
    private Review review2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        review1 = new Review();
        review1.setMediaId("100");
        review1.setScore(5);
        review1.setMediaType(MediaType.MOVIE);

        review2 = new Review();
        review2.setMediaId("200");
        review2.setScore(4);
        review2.setMediaType(MediaType.MOVIE);
    }

    @Test
    void shouldReturnEmptyListWhenNoReviewsFound() {
        when(reviewRepository.findTopByMemberAndMediaType(eq(1L), eq(MediaType.MOVIE), any(PageRequest.class)))
                .thenReturn(Collections.emptyList());

        var result = recommendationService.getRecommendations(1L, MediaType.MOVIE, "pt-BR");
        assertTrue(result.isEmpty());
    }




}
