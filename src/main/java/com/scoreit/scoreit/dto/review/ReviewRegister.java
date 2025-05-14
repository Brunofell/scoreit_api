package com.scoreit.scoreit.dto.review;

public record ReviewRegister(
        String mediaId,
        String mediaType,
        String memberId,
        int score,
        String memberReview,
        boolean spoiler
) {
}
