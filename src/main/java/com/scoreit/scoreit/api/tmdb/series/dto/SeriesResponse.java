package com.scoreit.scoreit.api.tmdb.series.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record SeriesResponse(
        int page,
        int total_results,
        @JsonProperty("total_pages") int totalPages, // caso o nome do atributo for diferente do que vem no json
        List<Series> results
) {
}
