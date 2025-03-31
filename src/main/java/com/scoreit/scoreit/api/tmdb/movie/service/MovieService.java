package com.scoreit.scoreit.api.tmdb.movie.service;

import com.scoreit.scoreit.api.tmdb.movie.dto.Movie;
import com.scoreit.scoreit.api.tmdb.movie.dto.MovieResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MovieService {

    private String apiKey = "4b6d83522f71b2da2b7b52c4386131e0";
    private String baseUrl = "https://api.themoviedb.org/3";

    @Autowired
    private RestTemplate restTemplate;

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
}
