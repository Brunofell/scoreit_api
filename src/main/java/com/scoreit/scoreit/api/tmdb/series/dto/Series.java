package com.scoreit.scoreit.api.tmdb.series.dto;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

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
        String content_rating
) {
    public String getPosterUrl() {
        return "https://image.tmdb.org/t/p/w500" + poster_path;
    }
    public String getBackdropUrl() {
        return "https://image.tmdb.org/t/p/w500" + backdrop_path;
    }

    public record BasicSeason(
            int season_number,
            String name,
            String air_date,
            String overview,
            String poster_path,
            int episode_count
    ) {}

}
