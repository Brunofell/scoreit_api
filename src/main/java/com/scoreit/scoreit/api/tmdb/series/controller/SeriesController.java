package com.scoreit.scoreit.api.tmdb.series.controller;

import com.scoreit.scoreit.api.tmdb.series.dto.Series;
import com.scoreit.scoreit.api.tmdb.series.dto.SeriesResponse;
import com.scoreit.scoreit.api.tmdb.series.service.SeriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}
