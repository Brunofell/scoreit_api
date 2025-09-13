package com.scoreit.scoreit.api.tmdb.series.controller;

import com.scoreit.scoreit.api.tmdb.series.dto.*;
import com.scoreit.scoreit.api.tmdb.series.service.SeriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/series")
public class SeriesController {
    @Autowired
    private SeriesService service;

    // default language can be overridden with ?language=en-US (ou pt-BR, etc.)
    @GetMapping("/get/page/{page}")
    public SeriesResponse getSeries(@PathVariable int page,
                                    @RequestParam(defaultValue = "pt-BR") String language){
        return service.getPopularSeries(page, language);
    }

    @GetMapping("/get/{id}")
    public Series getSeriesById(@PathVariable int id,
                                @RequestParam(defaultValue = "pt-BR") String language) {
        return service.getSeriesById(id, language);
    }

    @GetMapping("/now/{page}")
    public SeriesResponse getSeriesOnAir(@PathVariable int page,
                                         @RequestParam(defaultValue = "pt-BR") String language) {
        return service.getSeriesOnAir(page, language);
    }

    @GetMapping("/{seriesId}/season/{seasonNumber}")
    public SeriesSeason getSeasonDetails(@PathVariable int seriesId,
                                         @PathVariable int seasonNumber,
                                         @RequestParam(defaultValue = "pt-BR") String language) {
        return service.getSeriesSeason(seriesId, seasonNumber, language);
    }

    @GetMapping("/genre/{genreId}/page/{page}")
    public SeriesResponse getPopularSeriesByGenre(@PathVariable int genreId,
                                                  @PathVariable int page,
                                                  @RequestParam(defaultValue = "pt-BR") String language) {
        return service.getPopularSeriesByGenre(genreId, page, language);
    }

    @GetMapping("/year/{year}/page/{page}")
    public SeriesResponse getPopularSeriesByYear(@PathVariable int year,
                                                 @PathVariable int page,
                                                 @RequestParam(defaultValue = "pt-BR") String language) {
        return service.getPopularSeriesByYear(year, page, language);
    }

    @GetMapping("/several")
    public List<Series> getSeveralSeriesByIds(@RequestParam List<Integer> ids,
                                              @RequestParam(defaultValue = "pt-BR") String language) {
        return service.getSeveralSeriesByIds(ids, language);
    }

    @GetMapping("/favorites/{memberId}")
    public List<Series> getFavoriteSeriesByMemberId(@PathVariable Long memberId,
                                                    @RequestParam(defaultValue = "pt-BR") String language) {
        return service.getFavoriteSeriesByMemberId(memberId, language);
    }

    @GetMapping("/{seriesId}/all-seasons")
    public List<SeriesSeason> getAllSeasons(@PathVariable int seriesId,
                                            @RequestParam(defaultValue = "pt-BR") String language) {
        return service.getAllSeasonsWithEpisodes(seriesId, language);
    }

    @GetMapping("/{seriesId}/details")
    public SeriesDetail getSeriesDetail(@PathVariable int seriesId,
                                        @RequestParam(defaultValue = "pt-BR") String language) {
        return service.getSeriesDetail(seriesId, language);
    }

    // search

    @GetMapping("/search/title/{title}")
    public SeriesResponse searchSeriesByTitle(@PathVariable String title,
                                              @RequestParam(defaultValue = "1") int page,
                                              @RequestParam(defaultValue = "pt-BR") String language) {
        return service.searchSeriesByTitle(title, page, language);
    }

    @GetMapping("/search/genre/{genre}")
    public SeriesResponse searchSeriesByGenre(@PathVariable String genre,
                                              @RequestParam(defaultValue = "1") int page,
                                              @RequestParam(defaultValue = "pt-BR") String language) {
        return service.searchSeriesByGenre(genre, page, language);
    }

    @GetMapping("/search/year/{year}")
    public SeriesResponse searchSeriesByYear(@PathVariable String year,
                                             @RequestParam(defaultValue = "1") int page,
                                             @RequestParam(defaultValue = "pt-BR") String language) {
        return service.searchSeriesByYear(year, page, language);
    }

    @GetMapping("/search/genres") // retorna generos
    public String getGenre(@RequestParam(defaultValue = "pt-BR") String language){
        return service.getGenres(language);
    }

    @GetMapping("/search/year/{year}/genre/{genre}")
    public SeriesResponse searchSeriesByYearAndGenre(
            @PathVariable String year,
            @PathVariable String genre,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "pt-BR") String language
    ) {
        return service.searchSeriesByYearAndGenre(year, genre, page, language);
    }

    @GetMapping("/search")
    public SeriesResponse searchSeries(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String year,
            @RequestParam(required = false) String genre,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "pt-BR") String language
    ) {
        return service.searchSeries(title, year, genre, page, language);
    }

}
