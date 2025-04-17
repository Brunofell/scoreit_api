package com.scoreit.scoreit.api.tmdb.series.service;

import com.scoreit.scoreit.api.tmdb.series.dto.Series;
import com.scoreit.scoreit.api.tmdb.series.dto.SeriesResponse;
import com.scoreit.scoreit.api.tmdb.series.dto.SeriesSeason;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SeriesService {

    private String apiKey = "4b6d83522f71b2da2b7b52c4386131e0";
    private String baseUrl = "https://api.themoviedb.org/3";
    private RestTemplate restTemplate;

    public SeriesService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    public SeriesResponse getPopularSeries(int page){ /*Pega todas as séries, 20 a cada página*/
        String url = String.format("%s/tv/popular?api_key=%s&language=pt-BR&page=%d", baseUrl, apiKey, page);
        return restTemplate.getForObject(url, SeriesResponse.class);
    }

    public Series getSeriesById(int id) {
        String url = String.format("%s/tv/%d?api_key=%s&language=pt-BR", baseUrl, id, apiKey);
        return restTemplate.getForObject(url, Series.class);
    }

    public SeriesResponse getSeriesOnAir(int page) {
        String url = String.format("%s/tv/on_the_air?api_key=%s&language=pt-BR&page=%d", baseUrl, apiKey, page);
        return restTemplate.getForObject(url, SeriesResponse.class);
    }

    public SeriesResponse getPopularSeriesByGenre(int genreId, int page) {
        String url = String.format("%s/discover/tv?api_key=%s&language=pt-BR&with_genres=%d&page=%d", baseUrl, apiKey, genreId, page);
        return restTemplate.getForObject(url, SeriesResponse.class);
    }

    public SeriesResponse getPopularSeriesByYear(int year, int page) {
        String url = String.format("%s/discover/tv?api_key=%s&language=pt-BR&first_air_date_year=%d&page=%d", baseUrl, apiKey, year, page);
        return restTemplate.getForObject(url, SeriesResponse.class);
    }
    public SeriesSeason getSeriesSeason(int seriesId, int seasonNumber) {
        String url = String.format("%s/tv/%d/season/%d?api_key=%s&language=pt-BR", baseUrl, seriesId, seasonNumber, apiKey);
        return restTemplate.getForObject(url, SeriesSeason.class);
    }
}
