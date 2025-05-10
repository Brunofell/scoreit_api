package com.scoreit.scoreit.api.tmdb.series.service;

import com.scoreit.scoreit.api.tmdb.movie.dto.CreditsResponse;
import com.scoreit.scoreit.api.tmdb.series.dto.*;
import com.scoreit.scoreit.entity.FavoriteListContent;
import com.scoreit.scoreit.service.FavoriteListContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SeriesService {

    private String apiKey = "4b6d83522f71b2da2b7b52c4386131e0";
    private String baseUrl = "https://api.themoviedb.org/3";
    private RestTemplate restTemplate;

    public SeriesService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    @Autowired
    private FavoriteListContentService favoriteListContentService;

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

    public List<Series> getSeveralSeriesByIds(List<Integer> ids) {
        return ids.stream()
                .map(this::getSeriesById)
                .collect(Collectors.toList());
    }

    public List<Series> getFavoriteSeriesByMemberId(Long memberId) {
        List<FavoriteListContent> favorites = favoriteListContentService.getFavoriteListContent(memberId);

        List<Integer> seriesIds = favorites.stream()
                .filter(content -> "series".equalsIgnoreCase(content.getMediaType()))
                .map(content -> {
                    try {
                        return Integer.parseInt(content.getMediaId());
                    } catch (NumberFormatException e) {
                        return null;
                    }
                })
                .filter(id -> id != null)
                .collect(Collectors.toList());

        return getSeveralSeriesByIds(seriesIds);
    }


    public List<SeriesSeason> getAllSeasonsWithEpisodes(int seriesId) {
        Series series = getSeriesById(seriesId);
        return series.seasons().stream()
                .map(season -> getSeriesSeason(seriesId, season.season_number()))
                .collect(Collectors.toList());
    }

    public List<CastMember> getCast(int seriesId) {
        String url = String.format("%s/tv/%d/credits?api_key=%s&language=pt-BR", baseUrl, seriesId, apiKey);
        CreditsResponse credits = restTemplate.getForObject(url, CreditsResponse.class);
        return credits.cast().stream()
                .map(c -> new CastMember(c.name(), c.character(), c.profile_path()))
                .collect(Collectors.toList());
    }

    public List<CrewMember> getDirectors(int seriesId) {
        String url = String.format("%s/tv/%d/credits?api_key=%s&language=pt-BR", baseUrl, seriesId, apiKey);
        CreditsResponse credits = restTemplate.getForObject(url, CreditsResponse.class);
        return credits.crew().stream()
                .filter(c -> "Director".equals(c.job()))
                .map(c -> new CrewMember(c.name(), c.job()))
                .collect(Collectors.toList());
    }

    public List<String> getGenres(Series series) {
        return series.genres() != null
                ? series.genres().stream().map(Genre::name).collect(Collectors.toList())
                : List.of();
    }

    public String getContentRating(int seriesId) {
        String url = String.format("%s/tv/%d/content_ratings?api_key=%s", baseUrl, seriesId, apiKey);
        ContentRatingResponse ratingResponse = restTemplate.getForObject(url, ContentRatingResponse.class);
        return ratingResponse != null && ratingResponse.results() != null
                ? ratingResponse.results().stream()
                .filter(r -> "BR".equalsIgnoreCase(r.iso_3166_1()))
                .map(ContentRatingResult::rating)
                .findFirst()
                .orElse("N/A")
                : "N/A";
    }



    public SeriesDetail getSeriesDetail(int seriesId) {
        Series series = getSeriesById(seriesId);
        List<SeriesSeason> seasons = getAllSeasonsWithEpisodes(seriesId);
        List<CastMember> cast = getCast(seriesId);
        List<CrewMember> directors = getDirectors(seriesId);
        List<String> genres = getGenres(series); // <-- chamada aqui
        String contentRating = getContentRating(seriesId); // <-- e aqui

        return new SeriesDetail(
                series.id(),
                series.name(),
                series.overview(),
                "https://image.tmdb.org/t/p/w500" + series.poster_path(),
                "https://image.tmdb.org/t/p/w500" + series.backdrop_path(),
                series.vote_average(),
                series.release_date(),
                seasons,
                cast,
                directors,
                genres,
                contentRating
        );
    }



}
