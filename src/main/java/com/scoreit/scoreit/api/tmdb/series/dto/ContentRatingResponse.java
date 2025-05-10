package com.scoreit.scoreit.api.tmdb.series.dto;

import java.util.List;

public record ContentRatingResponse(
        List<ContentRatingResult> results
) {}
