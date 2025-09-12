package com.scoreit.scoreit.dto.badge;

public record BadgeResponse(
        Long id,
        String name,
        String description,
        String code
) {}
