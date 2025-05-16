package com.scoreit.scoreit.api.music.spotify.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.scoreit.scoreit.api.music.spotify.client.ArtistSpotifyClient;
import com.scoreit.scoreit.api.music.spotify.client.AuthSpotifyClient;
import com.scoreit.scoreit.api.music.spotify.dto.oauth.LoginRequest;
import com.scoreit.scoreit.api.music.spotify.service.ArtistSpotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/spotify/api")
public class ArtistSpotifyController {

    @Autowired
    private ArtistSpotifyService artistSpotifyService;
    @Autowired
    private AuthSpotifyClient authSpotifyClient;
    @Autowired
    private ArtistSpotifyClient artistSpotifyClient;

    @GetMapping("/artist-image")
    public ResponseEntity<?> getArtistImage(@RequestParam("name") String artistName) {
        return artistSpotifyService.getArtistImage(artistName);
    }

    @GetMapping("/artists")
    public ResponseEntity<?> getSeveralArtists(@RequestParam("ids") List<String> ids) {
        return artistSpotifyService.getSeveralArtistsByIds(ids);
    }

    // get artistas e search artistas

    @GetMapping("/artist/search")
    public ResponseEntity<JsonNode> searchArtist(
            @RequestParam("query") String query,
            @RequestParam(value = "limit", defaultValue = "10") int limit
    ) {
        var request = new LoginRequest(
                "client_credentials",
                "46f327a02d944095a28863edd7446a50",
                "3debe93c67ed487a841689865e56ac18"
        );

        String token = authSpotifyClient.login(request).getAccess_token();

        JsonNode response = artistSpotifyClient.searchArtists("Bearer " + token, query, "artist", limit);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/artist/{id}")
    public ResponseEntity<JsonNode> getArtistById(@PathVariable("id") String artistId) {
        var request = new LoginRequest(
                "client_credentials",
                "46f327a02d944095a28863edd7446a50",
                "3debe93c67ed487a841689865e56ac18"
        );

        String token = authSpotifyClient.login(request).getAccess_token();

        JsonNode artistDetails = artistSpotifyClient.getArtist("Bearer " + token, artistId);

        return ResponseEntity.ok(artistDetails);
    }


}