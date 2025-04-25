package com.scoreit.scoreit.api.music.spotify.controller;

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

    @GetMapping("/artist-image")
    public ResponseEntity<?> getArtistImage(@RequestParam("name") String artistName) {
        return artistSpotifyService.getArtistImage(artistName);
    }

    @GetMapping("/artists")
    public ResponseEntity<?> getSeveralArtists(@RequestParam("ids") List<String> ids) {
        return artistSpotifyService.getSeveralArtistsByIds(ids);
    }




}