package com.scoreit.scoreit.api.tmdb.movie.dto;

import java.util.List;

public record RatingsResponse(List<CountryRelease> results) {

    public String getCertification(String countryCode) {
        return results.stream()
                .filter(r -> r.iso_3166_1().equalsIgnoreCase(countryCode))
                .flatMap(r -> r.release_dates().stream())
                .filter(rd -> rd.certification() != null && !rd.certification().isEmpty())
                .map(ReleaseDateInfo::certification)
                .findFirst()
                .orElse("N/A");
    }

    public record CountryRelease(String iso_3166_1, List<ReleaseDateInfo> release_dates) {}

    public record ReleaseDateInfo(String certification, String release_date, int type) {}
}

