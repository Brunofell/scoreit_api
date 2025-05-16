package com.scoreit.scoreit.api.tmdb.movie.dto.person;

public record KnownFor(
        int id,
        String title, // para filmes
        String name,  // para séries
        String media_type, // "movie" ou "tv"
        String poster_path,
        String overview
) {}