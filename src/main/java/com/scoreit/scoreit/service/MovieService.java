package com.scoreit.scoreit.service;

import com.scoreit.scoreit.dto.movie.Movie;
import com.scoreit.scoreit.dto.movie.MovieResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MovieService {

    private String apiKey = "4b6d83522f71b2da2b7b52c4386131e0";
    private String baseUrl = "https://api.themoviedb.org/3";
    private RestTemplate restTemplate;

    public MovieService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    public MovieResponse getMovies(int page){ /*Pega todos os filmes, 20 a cada página*/
        String url = String.format("%s/movie/popular?api_key=%s&language=pt-BR&page=%d", baseUrl, apiKey, page);
        return restTemplate.getForObject(url, MovieResponse.class);
    }

    public Movie getMovieById(int id) {
        String url = String.format("%s/movie/%d?api_key=%s&language=pt-BR", baseUrl, id, apiKey);
        return restTemplate.getForObject(url, Movie.class);
    }

}
