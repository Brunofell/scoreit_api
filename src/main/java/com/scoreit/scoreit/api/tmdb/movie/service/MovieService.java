package com.scoreit.scoreit.api.tmdb.movie.service;

import com.scoreit.scoreit.api.tmdb.movie.dto.Movie;
import com.scoreit.scoreit.api.tmdb.movie.dto.MovieMediaResponse;
import com.scoreit.scoreit.api.tmdb.movie.dto.MovieResponse;
import com.scoreit.scoreit.entity.FavoriteListContent;
import com.scoreit.scoreit.service.FavoriteListContentService;
import com.scoreit.scoreit.service.FavoriteListService;
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


}
