package com.scoreit.scoreit.api.music.lastfm.dto.artist;


import com.fasterxml.jackson.annotation.JsonProperty;

public record TopArtistsResponse(
        @JsonProperty("artists") TopArtistsData artists
) {}