package com.scoreit.scoreit.api.tmdb.movie.dto.person;

import java.util.List;

public record PersonSearchResponse(
        int page,
        List<Person> results,
        int total_pages,
        int total_results
) {}
