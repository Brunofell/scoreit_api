package com.scoreit.scoreit.api.music.spotify.dto.track;

public class TrackImageResponse {
    private String trackName;
    private String imageUrl;

    public TrackImageResponse(String trackName, String imageUrl) {
        this.trackName = trackName;
        this.imageUrl = imageUrl;
    }

    public String getTrackName() {
        return trackName;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}