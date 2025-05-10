package com.scoreit.scoreit.api.tmdb.series.dto;

public record CastMember(String name, String character, String profile_path) {
    public String getProfileUrl() {
        return "https://image.tmdb.org/t/p/w500" + profile_path;
    }
}