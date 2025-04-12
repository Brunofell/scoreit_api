package com.scoreit.scoreit.api.music;

import com.scoreit.scoreit.api.music.lastfm.dto.artist.LastFmArtist;

public class UnifiedArtistResponse {
    private LastFmArtist lastFmArtist;
    private String spotifyImageUrl;

    public UnifiedArtistResponse(LastFmArtist lastFmArtist, String spotifyImageUrl) {
        this.lastFmArtist = lastFmArtist;
        this.spotifyImageUrl = spotifyImageUrl;
    }

    public LastFmArtist getLastFmArtist() {
        return lastFmArtist;
    }

    public void setLastFmArtist(LastFmArtist lastFmArtist) {
        this.lastFmArtist = lastFmArtist;
    }

    public String getSpotifyImageUrl() {
        return spotifyImageUrl;
    }

    public void setSpotifyImageUrl(String spotifyImageUrl) {
        this.spotifyImageUrl = spotifyImageUrl;
    }
}