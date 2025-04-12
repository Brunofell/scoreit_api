package com.scoreit.scoreit.api.music.lastfm.dto.track;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TrackResponse {
    @JsonProperty("track")
    private Track track;

    public Track getTrack() {
        return track;
    }

    public void setTrack(Track track) {
        this.track = track;
    }
}