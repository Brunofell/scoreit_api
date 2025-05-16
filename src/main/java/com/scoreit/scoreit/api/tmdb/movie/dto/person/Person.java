package com.scoreit.scoreit.api.tmdb.movie.dto.person;

import java.util.List;

public record Person(
        int id,
        String name,
        String profile_path,
        List<KnownFor> known_for
) {}