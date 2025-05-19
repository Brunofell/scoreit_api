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

    @GetMapping("/get/page/{page}")
    public SeriesResponse getSeries(@PathVariable int page){
        return service.getPopularSeries(page);
    }

    @GetMapping("/get/{id}")
    public Series getSeriesById(@PathVariable int id) {
        return service.getSeriesById(id);
    }

    @GetMapping("/now/{page}")
    public SeriesResponse getSeriesOnAir(@PathVariable int page) {return service.getSeriesOnAir(page);}

    @GetMapping("/{seriesId}/season/{seasonNumber}")
    public SeriesSeason getSeasonDetails(@PathVariable int seriesId, @PathVariable int seasonNumber) {
        return service.getSeriesSeason(seriesId, seasonNumber);
    }

    @GetMapping("/genre/{genreId}/page/{page}")
    public SeriesResponse getPopularSeriesByGenre(@PathVariable int genreId, @PathVariable int page) {
        return service.getPopularSeriesByGenre(genreId, page);
    }

    @GetMapping("/year/{year}/page/{page}")
    public SeriesResponse getPopularSeriesByYear(@PathVariable int year, @PathVariable int page) {
        return service.getPopularSeriesByYear(year, page);
    }

    @GetMapping("/several")
    public List<Series> getSeveralSeriesByIds(@RequestParam List<Integer> ids) {
        return service.getSeveralSeriesByIds(ids);
    }

    @GetMapping("/favorites/{memberId}")
    public List<Series> getFavoriteSeriesByMemberId(@PathVariable Long memberId) {
        return service.getFavoriteSeriesByMemberId(memberId);
    }

    @GetMapping("/{seriesId}/all-seasons")
    public List<SeriesSeason> getAllSeasons(@PathVariable int seriesId) {
        return service.getAllSeasonsWithEpisodes(seriesId);
    }

    @GetMapping("/{seriesId}/details")
    public SeriesDetail getSeriesDetail(@PathVariable int seriesId) {
        return service.getSeriesDetail(seriesId);
    }

    // search

    @GetMapping("/search/title/{title}")
    public SeriesResponse searchSeriesByTitle(@PathVariable String title, @RequestParam(defaultValue = "1") int page) {
        return service.searchSeriesByTitle(title, page);
    }

    @GetMapping("/search/genre/{genre}")
    public SeriesResponse searchSeriesByGenre(@PathVariable String genre, @RequestParam(defaultValue = "1") int page) {
        return service.searchSeriesByGenre(genre, page);
    }

    @GetMapping("/search/year/{year}")
    public SeriesResponse searchSeriesByYear(@PathVariable String year, @RequestParam(defaultValue = "1") int page) {
        return service.searchSeriesByYear(year, page);
    }

    @GetMapping("/search/genres") // retorna generos
    public String getGenre(){
        return service.getGenres();
    }

    @GetMapping("/search/year/{year}/genre/{genre}")
    public SeriesResponse searchSeriesByYearAndGenre(
            @PathVariable String year,
            @PathVariable String genre,
            @RequestParam(defaultValue = "1") int page
    ) {
        return service.searchSeriesByYearAndGenre(year, genre, page);
    }

    @GetMapping("/search")
    public SeriesResponse searchSeries(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String year,
            @RequestParam(required = false) String genre,
            @RequestParam(defaultValue = "1") int page
    ) {
        return service.searchSeries(title, year, genre, page);
    }

}
