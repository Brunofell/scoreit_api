package com.scoreit.scoreit.api.tmdb.series.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Series(
        int id,
        String name,
        String overview,
        String poster_path,
        String backdrop_path,
        double vote_average,
        @JsonProperty("first_air_date") String release_date,
        @JsonProperty("seasons") List<BasicSeason> seasons,
        @JsonProperty("genres") List<Genre> genres,
        List<Integer> genre_ids,              // <-- IMPORTANTE
        String content_rating
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

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static record BasicSeason(
            int season_number,
            String name,
            String air_date,
            String overview,
            String poster_path,
            int episode_count
    ) {}
}
