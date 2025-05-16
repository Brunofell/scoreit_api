package com.scoreit.scoreit.api.tmdb.movie.controller;

import com.scoreit.scoreit.api.tmdb.movie.dto.Movie;
import com.scoreit.scoreit.api.tmdb.movie.dto.MovieDetail;
import com.scoreit.scoreit.api.tmdb.movie.dto.MovieMediaResponse;
import com.scoreit.scoreit.api.tmdb.movie.dto.MovieResponse;
import com.scoreit.scoreit.api.tmdb.movie.dto.person.PersonSearchResponse;
import com.scoreit.scoreit.api.tmdb.movie.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/favorites/{memberId}")
    public List<Movie> getFavoriteMoviesByMemberId(@PathVariable Long memberId) {
        return service.getFavoriteMoviesByMemberId(memberId);
    }

    @GetMapping("/{id}/details")
    public ResponseEntity<MovieDetail> getMovieDetails(@PathVariable int id) {
        MovieDetail movieDetail = service.getMovieDetails(id);
        return ResponseEntity.ok(movieDetail);
    }


    // search

    @GetMapping("/search/title/{title}")
    public MovieResponse seachMovieByTitle(@PathVariable String title, @RequestParam(defaultValue = "1") int page) {
        return service.searchMovieByTitle(title, page);
    }

    @GetMapping("/search/name/{name}")
    public PersonSearchResponse searchMovieByName(@PathVariable String name, @RequestParam(defaultValue = "1") int page) {
        return service.searchPersonByName(name, page);
    }

    @GetMapping("/search/genre/{genre}")
    public MovieResponse searchMovieByGenre(@PathVariable String genre, @RequestParam(defaultValue = "1") int page) {
        return service.searchMovieByGenre(genre, page);
    }

    @GetMapping("/search/year/{year}")
    public MovieResponse searchMovieByYear(@PathVariable String year, @RequestParam(defaultValue = "1") int page) {
        return service.searchMovieByYear(year, page);
    }

    @GetMapping("/search/genres") // retorna generos
    public String getGenre(){
        return service.getGenres();
    }
}
