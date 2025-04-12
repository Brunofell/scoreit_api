package com.scoreit.scoreit.api.music.lastfm.dto.artist;

import com.fasterxml.jackson.annotation.JsonProperty;

public record LastFmArtistResponse(
        @JsonProperty("artist") LastFmArtist lastFmArtist
) {}