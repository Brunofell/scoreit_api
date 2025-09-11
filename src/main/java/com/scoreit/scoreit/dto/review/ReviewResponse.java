package com.scoreit.scoreit.dto.review;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ReviewResponse(
        Long id,
        String mediaId,
        String mediaType,
        Long memberId,
        int score,
        String memberReview,
        LocalDate watchDate, 
        boolean spoiler,
        LocalDateTime reviewDate
) {}
