package com.scoreit.scoreit.api.lastfm;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ArtistResponse(
        @JsonProperty("artist") Artist artist
) {}