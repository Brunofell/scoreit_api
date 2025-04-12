package com.scoreit.scoreit.api.lastfm.dto.artist;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.scoreit.scoreit.api.lastfm.dto.Image;

public record Artist(
        String name,
        @JsonProperty("url") String profileUrl,
        @JsonProperty("image") Image[] images
) {}


