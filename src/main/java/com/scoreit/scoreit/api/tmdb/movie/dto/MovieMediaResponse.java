package com.scoreit.scoreit.api.tmdb.movie.dto;

import java.util.List;

public record MovieMediaResponse(
        List<Video> results
) {
    public record Video(String id, String key, String name, String site, String type) {
    }
}