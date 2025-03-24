package com.scoreit.scoreit.controller;

import com.scoreit.scoreit.dto.movie.MovieResponse;
import com.scoreit.scoreit.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movie")
public class MovieController {

    @Autowired
    private MovieService service;

    @GetMapping("/get")
    public MovieResponse getMovies(@RequestParam(defaultValue = "1") int page){
        return service.getMovies(page);
    }


}
