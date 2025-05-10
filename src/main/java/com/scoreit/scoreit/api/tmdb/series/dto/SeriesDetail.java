package com.scoreit.scoreit.api.tmdb.series.dto;

import java.util.List;

public record SeriesDetail(
        int id,
        String name,
        String overview,
        String poster_path,
        String backdrop_path,
        double vote_average,
        String release_date,
        List<SeriesSeason> seasons,
        List<CastMember> cast,
        List<CrewMember> directors,
        List<String> genres,
        String content_rating
) {
    public String getPosterUrl() {
        return "https://image.tmdb.org/t/p/w500" + poster_path;
    }

    public String getBackdropUrl() {
        return "https://image.tmdb.org/t/p/w500" + backdrop_path;
    }
}
