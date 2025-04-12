package com.scoreit.scoreit.api.lastfm.dto.artist;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ArtistResponse(
        @JsonProperty("artist") Artist artist
) {}