package com.scoreit.scoreit.dto.series;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.scoreit.scoreit.dto.movie.Movie;

import java.util.List;

public record SeriesResponse(
        int page,
        int total_results,
        @JsonProperty("total_pages") int totalPages, // caso o nome do atributo for diferente do que vem no json
        List<Series> results
) {
}
