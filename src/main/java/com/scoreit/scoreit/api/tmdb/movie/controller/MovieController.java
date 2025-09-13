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

    // Popular movies
    @GetMapping("/get/page/{page}")
    public MovieResponse getPopularMovies(
            @PathVariable int page,
            @RequestParam(defaultValue = "pt-BR") String language) {
        return service.getPopularMovies(page, language);
    }

    @GetMapping("/id/{id}")
    public Movie getMovieById(
            @PathVariable int id,
            @RequestParam(defaultValue = "pt-BR") String language) {
        return service.getMovieById(id, language);
    }

    @GetMapping("/now/{page}")
    public MovieResponse getMoviesNowPlaying(
            @PathVariable int page,
            @RequestParam(defaultValue = "pt-BR") String language) {
        return service.getMoviesNowPlaying(page, language);
    }

    @GetMapping("/upcoming/{page}")
    public MovieResponse getUpcomingMovies(
            @PathVariable int page,
            @RequestParam(defaultValue = "pt-BR") String language) {
        return service.getUpcomingMovies(page, language);
    }

    @GetMapping("/media/{movieId}")
    public MovieMediaResponse getMovieMedia(
            @PathVariable String movieId,
            @RequestParam(defaultValue = "pt-BR") String language) {
        return service.getMovieMedia(movieId, language);
    }

    @GetMapping("/several")
    public List<Movie> getSeveralMoviesByIds(
            @RequestParam List<Integer> ids,
            @RequestParam(defaultValue = "pt-BR") String language) {
        return service.getSeveralMoviesByIds(ids, language);
    }

    @GetMapping("/favorites/{memberId}")
    public List<Movie> getFavoriteMoviesByMemberId(
            @PathVariable Long memberId,
            @RequestParam(defaultValue = "pt-BR") String language) {
        return service.getFavoriteMoviesByMemberId(memberId, language);
    }

    @GetMapping("/{id}/details")
    public ResponseEntity<MovieDetail> getMovieDetails(
            @PathVariable int id,
            @RequestParam(defaultValue = "pt-BR") String language) {
        MovieDetail movieDetail = service.getMovieDetails(id, language);
        return ResponseEntity.ok(movieDetail);
    }

    // Search
    @GetMapping("/search/title/{title}")
    public MovieResponse seachMovieByTitle(
            @PathVariable String title,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "pt-BR") String language) {
        return service.searchMovieByTitle(title, page, language);
    }

    @GetMapping("/search/name/{name}")
    public PersonSearchResponse searchMovieByName(
            @PathVariable String name,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "pt-BR") String language) {
        return service.searchPersonByName(name, page, language);
    }

    @GetMapping("/search/genre/{genre}")
    public MovieResponse searchMovieByGenre(
            @PathVariable String genre,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "pt-BR") String language) {
        return service.searchMovieByGenre(genre, page, language);
    }

    @GetMapping("/search/year/{year}")
    public MovieResponse searchMovieByYear(
            @PathVariable String year,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "pt-BR") String language) {
        return service.searchMovieByYear(year, page, language);
    }

    @GetMapping("/search/genres")
    public String getGenre(
            @RequestParam(defaultValue = "pt-BR") String language) {
        return service.getGenres(language);
    }

    @GetMapping("/search/year/{year}/genre/{genre}")
    public MovieResponse searchMovieByYearAndGenre(
            @PathVariable String year,
            @PathVariable String genre,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "pt-BR") String language) {
        return service.searchMovieByYearAndGenre(year, genre, page, language);
    }




    @GetMapping("/search")
    public MovieResponse searchMovies(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String year,
            @RequestParam(required = false) String genre,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "pt-BR") String language) {
        return service.searchMovies(title, year, genre, page, language);
    }
}
