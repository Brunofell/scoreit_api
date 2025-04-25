package com.scoreit.scoreit.api.tmdb.movie.controller;

import com.scoreit.scoreit.api.tmdb.movie.dto.Movie;
import com.scoreit.scoreit.api.tmdb.movie.dto.MovieMediaResponse;
import com.scoreit.scoreit.api.tmdb.movie.dto.MovieResponse;
import com.scoreit.scoreit.api.tmdb.movie.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movie")
public class MovieController {

    @Autowired
    private MovieService service;

    @GetMapping("/get/page/{page}")
    public MovieResponse getPopularMovies(@PathVariable int page){
        return service.getPopularMovies(page);
    }

    @GetMapping("/id/{id}")
    public Movie getMovieById(@PathVariable int id) {
        return service.getMovieById(id);
    }

    @GetMapping("/now/{page}")
    public MovieResponse getMoviesNowPlaying(@PathVariable int page) {
        return service.getMoviesNowPlaying(page);
    }

    @GetMapping("/upcoming/{page}")
    public MovieResponse getUpcomingMovies(@PathVariable int page) {
        return service.getUpcomingMovies(page);
    }

    @GetMapping("/media/{movieId}")
    public MovieMediaResponse getMovieMedia(@PathVariable String movieId) {
        return service.getMovieMedia(movieId);
    }

    @GetMapping("/several")
    public List<Movie> getSeveralMoviesByIds(@RequestParam List<Integer> ids) {
        return service.getSeveralMoviesByIds(ids);
    }


}
