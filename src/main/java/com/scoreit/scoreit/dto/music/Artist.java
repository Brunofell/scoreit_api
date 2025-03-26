package com.scoreit.scoreit.dto.music;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Artist(
        String name,
        @JsonProperty("url") String profileUrl,
        @JsonProperty("image") Image[] images
) {}


