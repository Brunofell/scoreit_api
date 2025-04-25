package com.scoreit.scoreit.api.tmdb.series.controller;

import com.scoreit.scoreit.api.tmdb.series.dto.Series;
import com.scoreit.scoreit.api.tmdb.series.dto.SeriesResponse;
import com.scoreit.scoreit.api.tmdb.series.dto.SeriesSeason;
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
}
