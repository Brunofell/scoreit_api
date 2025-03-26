package com.scoreit.scoreit.dto.music;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ArtistResponse(
        @JsonProperty("artist") Artist artist
) {}