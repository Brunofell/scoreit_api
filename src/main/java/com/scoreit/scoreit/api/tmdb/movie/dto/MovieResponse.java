package com.scoreit.scoreit.api.tmdb.movie.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record MovieResponse (
        int page,
        int total_results,
        @JsonProperty("total_pages") int totalPages, // caso o nome do atributo for diferente do que vem no json
        List<Movie> results
){

}
