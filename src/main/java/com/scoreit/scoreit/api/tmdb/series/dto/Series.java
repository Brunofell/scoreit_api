package com.scoreit.scoreit.api.tmdb.series.dto;

public record Series(
        int id,
        String name,
        String overview,
        String poster_path ,
        String backdrop_path,
        double vote_average,
        String release_date
) {
    public String getPosterUrl() {
        return "https://image.tmdb.org/t/p/w500" + poster_path;
    }
    public String getBackdropUrl() {
        return "https://image.tmdb.org/t/p/w500" + backdrop_path;
    }
}
