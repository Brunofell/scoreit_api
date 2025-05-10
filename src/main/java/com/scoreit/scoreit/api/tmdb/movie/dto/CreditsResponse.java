package com.scoreit.scoreit.api.tmdb.movie.dto;

import java.util.List;

public record CreditsResponse(
        List<CastDTO> cast,
        List<CrewDTO> crew
) {
    public record CastDTO(String name, String character, String profile_path) {}
    public record CrewDTO(String name, String job) {}
}

