package com.scoreit.scoreit.dto.movie;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Movie(
        int id,
        String title,
        String overview,
        @JsonProperty("poster_path") String posterPath
) {}
