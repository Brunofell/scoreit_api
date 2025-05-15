package com.scoreit.scoreit.dto.review;

import java.time.LocalDateTime;

public record ReviewUpdate(
        Long id,
        Integer score,
        String memberReview,
        LocalDateTime watchDate,
        Boolean spoiler
) {
}
