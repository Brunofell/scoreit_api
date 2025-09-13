package com.scoreit.scoreit.api.tmdb.movie.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Movie(
        int id,
        String title,
        String overview,
        String poster_path,
        String backdrop_path,
        double vote_average,
        String release_date,
        List<Integer> genre_ids,              // <-- IMPORTANTE
        List<Genre> genres
) {
    public String getPosterUrl() {
        return poster_path != null
                ? "https://image.tmdb.org/t/p/w500" + poster_path
                : null;
    }

    public String getBackdropUrl() {
        return backdrop_path != null
                ? "https://image.tmdb.org/t/p/w500" + backdrop_path
                : null;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static record Genre(int id, String name) {}
}
