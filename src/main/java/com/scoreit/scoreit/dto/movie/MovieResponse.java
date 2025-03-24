package com.scoreit.scoreit.dto.movie;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record MovieResponse (
        int page,
        @JsonProperty("total_results") int totalResults,
        @JsonProperty("total_pages") int totalPages,
        List<Movie> results
){
}
