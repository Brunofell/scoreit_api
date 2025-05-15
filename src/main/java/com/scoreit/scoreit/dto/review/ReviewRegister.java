package com.scoreit.scoreit.dto.review;

import java.time.LocalDateTime;

public record ReviewRegister(
        String mediaId,
        String mediaType,
        String memberId,
        int score,
        String memberReview,
        LocalDateTime watchDate,
        boolean spoiler
) {
}
