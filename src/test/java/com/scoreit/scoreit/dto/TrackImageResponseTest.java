package com.scoreit.scoreit.dto;


import org.junit.jupiter.api.Test;
import com.scoreit.scoreit.api.music.spotify.dto.track.TrackImageResponse;
import static org.junit.jupiter.api.Assertions.*;

class TrackImageResponseTest {

    @Test
    void shouldStoreValuesCorrectly() {
        TrackImageResponse track = new TrackImageResponse("Song Name", "http://image.url/img.jpg");

        assertEquals("Song Name", track.getTrackName());
        assertEquals("http://image.url/img.jpg", track.getImageUrl());
    }
}
