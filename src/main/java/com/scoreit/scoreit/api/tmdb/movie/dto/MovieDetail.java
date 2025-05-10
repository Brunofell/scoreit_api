package com.scoreit.scoreit.api.tmdb.movie.dto;


import java.util.List;

public record MovieDetail(
        int id,
        String title,
        String overview,
        String poster_path,
        String backdrop_path,
        double vote_average,
        String release_date,
        int runtime,
        List<Genre> genres,
        String original_language,
        String certification, // classificação indicativa
        String status,
        long budget,
        long revenue,
        List<Company> production_companies,
        List<Country> production_countries,
        List<CastMember> cast,
        List<CrewMember> directors,
        List<Movie> recommendations,
        List<Movie> similar
) {
    public String getPosterUrl() {
        return "https://image.tmdb.org/t/p/w500" + poster_path;
    }

    public String getBackdropUrl() {
        return "https://image.tmdb.org/t/p/w500" + backdrop_path;
    }

    public record CastMember(String name, String character, String profile_path) {
        public String getProfileUrl() {
            if (profile_path != null) {
                return "https://image.tmdb.org/t/p/w500" + profile_path;
            }
            return null;
        }
    }

    public record CrewMember(String name, String job) {}
}
