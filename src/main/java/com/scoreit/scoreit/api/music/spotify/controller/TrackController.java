package com.scoreit.scoreit.api.music.spotify.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.scoreit.scoreit.api.music.spotify.client.AuthSpotifyClient;
import com.scoreit.scoreit.api.music.spotify.client.TrackSpotifyClient;
import com.scoreit.scoreit.api.music.spotify.dto.oauth.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/spotify/track")
public class TrackController {

    @Autowired
    private TrackSpotifyClient trackSpotifyClient;
    @Autowired
    private AuthSpotifyClient authSpotifyClient;


    @GetMapping("/search")
    public ResponseEntity<JsonNode> searchTrack(
            @RequestParam("query") String query,
            @RequestParam(value = "limit", defaultValue = "10") int limit
    ) {
        var request = new LoginRequest(
                "client_credentials",
                "46f327a02d944095a28863edd7446a50",
                "3debe93c67ed487a841689865e56ac18");

        String token = authSpotifyClient.login(request).getAccess_token();

        JsonNode response = trackSpotifyClient.searchTrack("Bearer " + token, query, "track", limit);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JsonNode> getTrackById(@PathVariable("id") String trackId) {
        var request = new LoginRequest(
                "client_credentials",
                "46f327a02d944095a28863edd7446a50",
                "3debe93c67ed487a841689865e56ac18");

        String token = authSpotifyClient.login(request).getAccess_token();

        JsonNode trackDetails = trackSpotifyClient.getTrack("Bearer " + token, trackId);

        return ResponseEntity.ok(trackDetails);
    }




}
