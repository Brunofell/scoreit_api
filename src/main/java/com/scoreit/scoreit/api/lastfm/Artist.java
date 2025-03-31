package com.scoreit.scoreit.api.lastfm;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Artist(
        String name,
        @JsonProperty("url") String profileUrl,
        @JsonProperty("image") Image[] images
) {}


