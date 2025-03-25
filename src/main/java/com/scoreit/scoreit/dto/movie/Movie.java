package com.scoreit.scoreit.dto.movie;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Movie(
        int id,
        String title,
        String overview,
        String poster_path ,
        double vote_average,
        String release_date
) {
    public String getPosterUrl() {
        return "https://image.tmdb.org/t/p/w500" + poster_path;
    }
}
