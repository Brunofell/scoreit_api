package com.scoreit.scoreit.api.tmdb.series.dto;

import java.util.List;

public record SeriesSeason(
        int id,
        String name,
        String overview,
        int season_number,
        String poster_path,
        String air_date,
        List<Episode> episodes
) {}