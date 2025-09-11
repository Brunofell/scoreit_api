package com.scoreit.scoreit.dto.review;

import java.time.LocalDate;

public record ReviewUpdate(
        Long id,
        Integer score,
        String memberReview,
        LocalDate watchDate,
        Boolean spoiler
) {
}
