package com.scoreit.scoreit.dto.review;

import java.time.LocalDate;

public record ReviewRegister(
        String mediaId,
        MediaType mediaType,
        Long memberId,
        int score,
        String memberReview,
        LocalDate watchDate,
        boolean spoiler
) {
}
