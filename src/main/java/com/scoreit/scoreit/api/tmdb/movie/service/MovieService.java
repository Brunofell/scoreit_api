package com.scoreit.scoreit.api.tmdb.movie.service;

import com.scoreit.scoreit.api.tmdb.movie.dto.*;
import com.scoreit.scoreit.api.tmdb.movie.dto.person.PersonSearchResponse;
import com.scoreit.scoreit.entity.FavoriteListContent;
import com.scoreit.scoreit.service.FavoriteListContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class MovieService {

    private String apiKey = "4b6d83522f71b2da2b7b52c4386131e0";
    private String baseUrl = "https://api.themoviedb.org/3";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private FavoriteListContentService favoriteListContentService;

    public MovieResponse getPopularMovies(int page, String language){
        String url = String.format("%s/movie/popular?api_key=%s&language=%s&page=%d", baseUrl, apiKey, language, page);
        return restTemplate.getForObject(url, MovieResponse.class);
    }

    public Movie getMovieById(int id, String language) {
        String url = String.format("%s/movie/%d?api_key=%s&language=%s", baseUrl, id, apiKey, language);
        return restTemplate.getForObject(url, Movie.class);
    }

    public MovieResponse getMoviesNowPlaying(int page, String language) {
        String url = String.format("%s/movie/now_playing?api_key=%s&language=%s&page=%d", baseUrl, apiKey, language, page);
        return restTemplate.getForObject(url, MovieResponse.class);
    }

    public MovieResponse getUpcomingMovies(int page, String language) {
        String url = String.format("%s/movie/upcoming?api_key=%s&language=%s&page=%d", baseUrl, apiKey, language, page);
        return restTemplate.getForObject(url, MovieResponse.class);
    }

    public MovieMediaResponse getMovieMedia(String movieId, String language) {
        String url = String.format("%s/movie/%s/videos?api_key=%s&language=%s", baseUrl, movieId, apiKey, language);
        return restTemplate.getForObject(url, MovieMediaResponse.class);
    }

    public List<Movie> getSeveralMoviesByIds(List<Integer> ids, String language) {
        return ids.stream()
                .map(id -> getMovieById(id, language))
                .toList();
    }

    public List<Movie> getFavoriteMoviesByMemberId(Long memberId, String language) {
        List<FavoriteListContent> favoriteContents = favoriteListContentService.getFavoriteListContent(memberId);

        List<Integer> movieIds = favoriteContents.stream()
                .filter(content -> "movie".equalsIgnoreCase(content.getMediaType()))
                .map(content -> {
                    try {
                        return Integer.parseInt(content.getMediaId());
                    } catch (NumberFormatException e) {
                        return null;
                    }
                })
                .filter(id -> id != null)
                .toList();

        return getSeveralMoviesByIds(movieIds, language);
    }

    public MovieDetail getMovieDetails(int id, String language) {
        MovieDetail baseMovie = fetchMovieBaseInfo(id, language);
        List<MovieDetail.CastMember> cast = fetchCast(id, language);
        List<MovieDetail.CrewMember> directors = fetchDirectors(id, language);
        String certification = fetchCertification(id);
        List<Movie> recommendations = fetchRecommendations(id, language);
        List<Movie> similar = fetchSimilarMovies(id, language);

        return new MovieDetail(
                baseMovie.id(),
                baseMovie.title(),
                baseMovie.overview(),
                "https://image.tmdb.org/t/p/w500" + baseMovie.poster_path(),
                "https://image.tmdb.org/t/p/w500" + baseMovie.backdrop_path(),
                baseMovie.vote_average(),
                baseMovie.release_date(),
                baseMovie.runtime(),
                baseMovie.genres(),
                baseMovie.original_language(),
                certification,
                baseMovie.status(),
                baseMovie.budget(),
                baseMovie.revenue(),
                baseMovie.production_companies(),
                baseMovie.production_countries(),
                cast,
                directors,
                recommendations,
                similar
        );
    }

    private MovieDetail fetchMovieBaseInfo(int id, String language) {
        String url = String.format("https://api.themoviedb.org/3/movie/%d?api_key=%s&language=%s", id, apiKey, language);
        return restTemplate.getForObject(url, MovieDetail.class);
    }

    private List<MovieDetail.CastMember> fetchCast(int id, String language) {
        String url = String.format("https://api.themoviedb.org/3/movie/%d/credits?api_key=%s&language=%s", id, apiKey, language);
        CreditsResponse credits = restTemplate.getForObject(url, CreditsResponse.class);
        return credits.cast().stream()
                .limit(10)
                .map(c -> new MovieDetail.CastMember(c.name(), c.character(), c.profile_path()))
                .toList();
    }

    private List<MovieDetail.CrewMember> fetchDirectors(int id, String language) {
        String url = String.format("https://api.themoviedb.org/3/movie/%d/credits?api_key=%s&language=%s", id, apiKey, language);
        CreditsResponse credits = restTemplate.getForObject(url, CreditsResponse.class);
        return credits.crew().stream()
                .filter(c -> "Director".equals(c.job()))
                .map(c -> new MovieDetail.CrewMember(c.name(), c.job()))
                .toList();
    }

    private String fetchCertification(int id) {
        String url = String.format("https://api.themoviedb.org/3/movie/%d/release_dates?api_key=%s", id, apiKey);
        RatingsResponse ratings = restTemplate.getForObject(url, RatingsResponse.class);
        return ratings.getCertification("BR"); // Método dentro do DTO RatingsResponse
    }

    private List<Movie> fetchRecommendations(int id, String language) {
        String url = String.format("https://api.themoviedb.org/3/movie/%d/recommendations?api_key=%s&language=%s", id, apiKey, language);
        MovieResponse response = restTemplate.getForObject(url, MovieResponse.class);
        return response.results();
    }

    private List<Movie> fetchSimilarMovies(int id, String language) {
        String url = String.format("https://api.themoviedb.org/3/movie/%d/similar?api_key=%s&language=%s", id, apiKey, language);
        MovieResponse response = restTemplate.getForObject(url, MovieResponse.class);
        return response.results();
    }


    // search
    public String getGenres(String language) {
        String url = String.format("https://api.themoviedb.org/3/genre/movie/list?language=%s&api_key=%s", language, apiKey);
        return restTemplate.getForObject(url, String.class);
    }

    public MovieResponse searchMovieByTitle(String title, int page, String language) {
        String url = String.format(
                "https://api.themoviedb.org/3/search/movie?query=%s&language=%s&page=%d&api_key=%s",
                title, language, page, apiKey
        );
        return restTemplate.getForObject(url, MovieResponse.class);
    }

    public PersonSearchResponse searchPersonByName(String name, int page, String language) {
        String url = String.format(
                "https://api.themoviedb.org/3/search/person?query=%s&language=%s&page=%d&api_key=%s",
                name, language, page, apiKey
        );
        return restTemplate.getForObject(url, PersonSearchResponse.class);
    }

    public MovieResponse searchMovieByGenre(String genre, int page, String language) {
        String url = String.format(
                "https://api.themoviedb.org/3/discover/movie?with_genres=%s&language=%s&page=%d&api_key=%s",
                genre, language, page, apiKey
        );
        return restTemplate.getForObject(url, MovieResponse.class);
    }

    public MovieResponse searchMovieByYear(String year, int page, String language) {
        String url = String.format(
                "https://api.themoviedb.org/3/discover/movie?primary_release_year=%s&language=%s&page=%d&api_key=%s",
                year, language, page, apiKey
        );
        return restTemplate.getForObject(url, MovieResponse.class);
    }

    public MovieResponse searchMovieByYearAndGenre(String year, String genre, int page, String language) {
        String url = String.format(
                "%s/discover/movie?primary_release_year=%s&with_genres=%s&language=%s&page=%d&api_key=%s",
                baseUrl, year, genre, language, page, apiKey
        );
        return restTemplate.getForObject(url, MovieResponse.class);
    }

    public MovieResponse searchMovies(String title, String year, String genre, int page, String language) {
        StringBuilder url = new StringBuilder(String.format(
                "%s/discover/movie?language=%s&page=%d&api_key=%s",
                baseUrl, language, page, apiKey
        ));

        if (title != null && !title.isBlank()) {
            url = new StringBuilder(String.format(
                    "%s/search/movie?query=%s&language=%s&page=%d&api_key=%s",
                    baseUrl, title, language, page, apiKey
            ));
        }

        if (year != null && !year.isBlank()) {
            url.append("&primary_release_year=").append(year);
        }

        if (genre != null && !genre.isBlank()) {
            url.append("&with_genres=").append(genre);
        }

        return restTemplate.getForObject(url.toString(), MovieResponse.class);
    }

}
