package com.scoreit.scoreit.api.tmdb.series.dto;

public record Episode(
        int id,
        String name,
        String overview,
        int episode_number,
        int season_number,
        String air_date,
        String still_path,
        double vote_average
) {
    public String getStillUrl() {
        if (still_path != null) {
            return "https://image.tmdb.org/t/p/w500" + still_path;
        }
        return null;
    }
}