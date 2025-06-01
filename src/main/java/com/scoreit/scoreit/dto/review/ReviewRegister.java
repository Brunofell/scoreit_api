package com.scoreit.scoreit.dto.review;

import java.time.LocalDateTime;

public record ReviewRegister(
        String mediaId,
        String mediaType,
        Long memberId,  // mudou aqui para Long
        int score,
        String memberReview,
        LocalDateTime watchDate,
        boolean spoiler
) {
}
