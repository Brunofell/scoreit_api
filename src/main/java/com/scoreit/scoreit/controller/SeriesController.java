package com.scoreit.scoreit.controller;

import com.scoreit.scoreit.dto.movie.Movie;
import com.scoreit.scoreit.dto.movie.MovieResponse;
import com.scoreit.scoreit.dto.series.Series;
import com.scoreit.scoreit.dto.series.SeriesResponse;
import com.scoreit.scoreit.service.MovieService;
import com.scoreit.scoreit.service.SeriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/series")
public class SeriesController {
    @Autowired
    private SeriesService service;

    @GetMapping("/get/page/{page}")
    public SeriesResponse getMovies(@RequestParam(defaultValue = "1") @PathVariable int page){
        return service.getSeries(page);
    }

    @GetMapping("/get/{id}")
    public Series getMovieById(@PathVariable int id) {
        return service.getSeriesById(id);
    }

    @GetMapping("/now/{page}")
    public SeriesResponse getSeriesNowPlaying(@PathVariable int page) {
        return service.getSeriesOnAir(page);
    }
}
