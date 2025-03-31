package com.scoreit.scoreit.api.tmdb.movie.dto;

public record Movie(
        int id,
        String title,
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
