package com.scoreit.scoreit.controller;

import com.scoreit.scoreit.dto.movie.Movie;
import com.scoreit.scoreit.dto.movie.MovieResponse;
import com.scoreit.scoreit.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movie")
public class MovieController {

    @Autowired
    private MovieService service;

    @GetMapping("/get/page/{page}")
    public MovieResponse getMovies(@RequestParam(defaultValue = "1") @PathVariable int page){
        return service.getMovies(page);
    }

    @GetMapping("/id/{id}")
    public Movie getMovieById(@PathVariable int id) {
        return service.getMovieById(id);
    }

    @GetMapping("/now/{page}")
    public MovieResponse getMoviesNowPlaying(@PathVariable int page) {
        return service.getMoviesNowPlaying(page);
    }

}
