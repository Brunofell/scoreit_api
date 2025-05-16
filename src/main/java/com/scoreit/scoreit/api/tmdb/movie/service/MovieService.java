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

    public MovieResponse getPopularMovies(int page){
        String url = String.format("%s/movie/popular?api_key=%s&language=pt-BR&page=%d", baseUrl, apiKey, page);
        return restTemplate.getForObject(url, MovieResponse.class);
    }

    public Movie getMovieById(int id) {
        String url = String.format("%s/movie/%d?api_key=%s&language=pt-BR", baseUrl, id, apiKey);
        return restTemplate.getForObject(url, Movie.class);
    }

    public MovieResponse getMoviesNowPlaying(int page) {
        String url = String.format("%s/movie/now_playing?api_key=%s&language=pt-BR&page=%d", baseUrl, apiKey, page);
        return restTemplate.getForObject(url, MovieResponse.class);
    }

    public MovieResponse getUpcomingMovies(int page) {
        String url = String.format("%s/movie/upcoming?api_key=%s&language=pt-BR&page=%d", baseUrl, apiKey, page);
        return restTemplate.getForObject(url, MovieResponse.class);
    }

    public MovieMediaResponse getMovieMedia(String movieId) {
        String url = String.format("%s/movie/%s/videos?api_key=%s&language=pt-BR", baseUrl, movieId, apiKey);
        return restTemplate.getForObject(url, MovieMediaResponse.class);
    }

    public List<Movie> getSeveralMoviesByIds(List<Integer> ids) {
        return ids.stream()
                .map(this::getMovieById)
                .toList();
    }

    public List<Movie> getFavoriteMoviesByMemberId(Long memberId) {
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

        return getSeveralMoviesByIds(movieIds);
    }

    public MovieDetail getMovieDetails(int id) {
        MovieDetail baseMovie = fetchMovieBaseInfo(id);
        List<MovieDetail.CastMember> cast = fetchCast(id);
        List<MovieDetail.CrewMember> directors = fetchDirectors(id);
        String certification = fetchCertification(id);
        List<Movie> recommendations = fetchRecommendations(id);
        List<Movie> similar = fetchSimilarMovies(id);

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

    private MovieDetail fetchMovieBaseInfo(int id) {
        String url = String.format("https://api.themoviedb.org/3/movie/%d?api_key=%s&language=pt-BR", id, apiKey);
        return restTemplate.getForObject(url, MovieDetail.class);
    }

    private List<MovieDetail.CastMember> fetchCast(int id) {
        String url = String.format("https://api.themoviedb.org/3/movie/%d/credits?api_key=%s&language=pt-BR", id, apiKey);
        CreditsResponse credits = restTemplate.getForObject(url, CreditsResponse.class);
        return credits.cast().stream()
                .limit(10)
                .map(c -> new MovieDetail.CastMember(c.name(), c.character(), c.profile_path()))
                .toList();
    }

    private List<MovieDetail.CrewMember> fetchDirectors(int id) {
        String url = String.format("https://api.themoviedb.org/3/movie/%d/credits?api_key=%s&language=pt-BR", id, apiKey);
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

    private List<Movie> fetchRecommendations(int id) {
        String url = String.format("https://api.themoviedb.org/3/movie/%d/recommendations?api_key=%s&language=pt-BR", id, apiKey);
        MovieResponse response = restTemplate.getForObject(url, MovieResponse.class);
        return response.results();
    }

    private List<Movie> fetchSimilarMovies(int id) {
        String url = String.format("https://api.themoviedb.org/3/movie/%d/similar?api_key=%s&language=pt-BR", id, apiKey);
        MovieResponse response = restTemplate.getForObject(url, MovieResponse.class);
        return response.results();
    }


    // search
    public String getGenres() {
        String url = String.format("https://api.themoviedb.org/3/genre/movie/list?language=pt-BR&api_key=%s", apiKey);
        return restTemplate.getForObject(url, String.class);
    }

    public MovieResponse searchMovieByTitle(String title, int page) {
        String url = String.format(
                "https://api.themoviedb.org/3/search/movie?query=%s&language=pt-BR&page=%d&api_key=%s",
                title, page, apiKey
        );
        return restTemplate.getForObject(url, MovieResponse.class);
    }

    public PersonSearchResponse searchPersonByName(String name, int page) {
        String url = String.format(
                "https://api.themoviedb.org/3/search/person?query=%s&language=pt-BR&page=%d&api_key=%s",
                name, page, apiKey
        );
        return restTemplate.getForObject(url, PersonSearchResponse.class);
    }

    public MovieResponse searchMovieByGenre(String genre, int page) {
        String url = String.format(
                "https://api.themoviedb.org/3/discover/movie?with_genres=%s&language=pt-BR&page=%d&api_key=%s",
                genre, page, apiKey
        );
        return restTemplate.getForObject(url, MovieResponse.class);
    }

    public MovieResponse searchMovieByYear(String year, int page) {
        String url = String.format(
                "https://api.themoviedb.org/3/discover/movie?primary_release_year=%s&language=pt-BR&page=%d&api_key=%s",
                year, page, apiKey
        );
        return restTemplate.getForObject(url, MovieResponse.class);
    }


}
