package com.scoreit.scoreit.api.music.lastfm.dto.artist;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.scoreit.scoreit.api.music.lastfm.dto.Image;

public record LastFmArtist(
        String name,
        @JsonProperty("image") Image[] images,
        String playcount,
        String listeners,
        String mbid
) {}


