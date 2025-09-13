package com.scoreit.scoreit.api.tmdb.series.service;

import com.scoreit.scoreit.api.tmdb.movie.dto.CreditsResponse;
import com.scoreit.scoreit.api.tmdb.movie.dto.MovieResponse;
import com.scoreit.scoreit.api.tmdb.movie.dto.person.PersonSearchResponse;
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

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private FavoriteListContentService favoriteListContentService;

    public SeriesResponse getPopularSeries(int page, String language){ /*Pega todas as séries, 20 a cada página*/
        String url = String.format("%s/tv/popular?api_key=%s&language=%s&page=%d", baseUrl, apiKey, language, page);
        return restTemplate.getForObject(url, SeriesResponse.class);
    }

    public Series getSeriesById(int id, String language) {
        // Se language for nulo ou vazio, força inglês
        String lang = (language == null || language.isBlank()) ? "en-US" : language;

        String url = String.format("%s/tv/%d?api_key=%s&language=%s", baseUrl, id, apiKey, lang);

        Series series = restTemplate.getForObject(url, Series.class);

        return series;
    }


    public SeriesResponse getSeriesOnAir(int page, String language) {
        String url = String.format("%s/tv/on_the_air?api_key=%s&language=%s&page=%d", baseUrl, apiKey, language, page);
        return restTemplate.getForObject(url, SeriesResponse.class);
    }

    public SeriesResponse getPopularSeriesByGenre(int genreId, int page, String language) {
        String url = String.format("%s/discover/tv?api_key=%s&language=%s&with_genres=%d&page=%d", baseUrl, apiKey, language, genreId, page);
        return restTemplate.getForObject(url, SeriesResponse.class);
    }

    public SeriesResponse getPopularSeriesByYear(int year, int page, String language) {
        String url = String.format("%s/discover/tv?api_key=%s&language=%s&first_air_date_year=%d&page=%d", baseUrl, apiKey, language, year, page);
        return restTemplate.getForObject(url, SeriesResponse.class);
    }

    public SeriesSeason getSeriesSeason(int seriesId, int seasonNumber, String language) {
        String url = String.format("%s/tv/%d/season/%d?api_key=%s&language=%s", baseUrl, seriesId, seasonNumber, apiKey, language);
        return restTemplate.getForObject(url, SeriesSeason.class);
    }

    public List<Series> getSeveralSeriesByIds(List<Integer> ids, String language) {
        return ids.stream()
                .map(id -> getSeriesById(id, language))
                .collect(Collectors.toList());
    }

    public List<Series> getFavoriteSeriesByMemberId(Long memberId, String language) {
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

        return getSeveralSeriesByIds(seriesIds, language);
    }

    public List<SeriesSeason> getAllSeasonsWithEpisodes(int seriesId, String language) {
        Series series = getSeriesById(seriesId, language);
        return series.seasons().stream()
                .map(season -> getSeriesSeason(seriesId, season.season_number(), language))
                .collect(Collectors.toList());
    }

    public List<CastMember> getCast(int seriesId, String language) {
        String url = String.format("%s/tv/%d/credits?api_key=%s&language=%s", baseUrl, seriesId, apiKey, language);
        CreditsResponse credits = restTemplate.getForObject(url, CreditsResponse.class);
        return credits.cast().stream()
                .map(c -> new CastMember(c.name(), c.character(), c.profile_path()))
                .collect(Collectors.toList());
    }

    public List<CrewMember> getDirectors(int seriesId, String language) {
        String url = String.format("%s/tv/%d/credits?api_key=%s&language=%s", baseUrl, seriesId, apiKey, language);
        CreditsResponse credits = restTemplate.getForObject(url, CreditsResponse.class);
        return credits.crew().stream()
                .filter(c -> "Director".equals(c.job()))
                .map(c -> new CrewMember(c.name(), c.job()))
                .collect(Collectors.toList());
    }

    public List<String> getGenres(Series series) {
        if (series.genres() == null) return List.of();
        return series.genres().stream()
                .map(g -> g.name())
                .collect(Collectors.toList());
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

    public SeriesDetail getSeriesDetail(int seriesId, String language) {
        String lang = (language == null || language.isBlank()) ? "en-US" : language;

        Series series = getSeriesById(seriesId, lang);
        List<SeriesSeason> seasons = getAllSeasonsWithEpisodes(seriesId, lang);
        List<CastMember> cast = getCast(seriesId, lang);
        List<CrewMember> directors = getDirectors(seriesId, lang);
        List<String> genres = getGenres(series);
        String contentRating = getContentRating(seriesId);

        return new SeriesDetail(
                series.id(),
                series.name(),
                series.overview(),
                series.poster_path() != null ? "https://image.tmdb.org/t/p/w500" + series.poster_path() : null,
                series.backdrop_path() != null ? "https://image.tmdb.org/t/p/w500" + series.backdrop_path() : null,
                series.vote_average(),
                series.release_date(),
                seasons,
                cast,
                directors,
                genres,
                contentRating
        );
    }

    // search

    public String getGenres(String language) {
        String url = String.format("%s/genre/tv/list?language=%s&api_key=%s", baseUrl, language, apiKey);
        return restTemplate.getForObject(url, String.class);
    }

    public SeriesResponse searchSeriesByTitle(String title, int page, String language) {
        String url = String.format(
                "%s/search/tv?query=%s&language=%s&page=%d&api_key=%s",
                baseUrl, title, language, page, apiKey
        );
        return restTemplate.getForObject(url, SeriesResponse.class);
    }

    public SeriesResponse searchSeriesByGenre(String genre, int page, String language) {
        String url = String.format(
                "%s/discover/tv?with_genres=%s&language=%s&page=%d&api_key=%s",
                baseUrl, genre, language, page, apiKey
        );
        return restTemplate.getForObject(url, SeriesResponse.class);
    }

    public SeriesResponse searchSeriesByYear(String year, int page, String language) {
        String url = String.format(
                "%s/discover/tv?first_air_date_year=%s&language=%s&page=%d&api_key=%s",
                baseUrl, year, language, page, apiKey
        );
        return restTemplate.getForObject(url, SeriesResponse.class);
    }

    public SeriesResponse searchSeriesByYearAndGenre(String year, String genre, int page, String language) {
        String url = String.format(
                "%s/discover/tv?first_air_date_year=%s&with_genres=%s&language=%s&page=%d&api_key=%s",
                baseUrl, year, genre, language, page, apiKey
        );
        return restTemplate.getForObject(url, SeriesResponse.class);
    }

    public SeriesResponse searchSeries(String title, String year, String genre, int page, String language) {
        StringBuilder url = new StringBuilder(String.format(
                "%s/discover/tv?language=%s&page=%d&api_key=%s",
                baseUrl, language, page, apiKey
        ));

        if (title != null && !title.isBlank()) {
            url = new StringBuilder(String.format(
                    "%s/search/tv?query=%s&language=%s&page=%d&api_key=%s",
                    baseUrl, title, language, page, apiKey
            ));
        }

        if (year != null && !year.isBlank()) {
            url.append("&first_air_date_year=").append(year);
        }

        if (genre != null && !genre.isBlank()) {
            url.append("&with_genres=").append(genre);
        }

        return restTemplate.getForObject(url.toString(), SeriesResponse.class);
    }

}
