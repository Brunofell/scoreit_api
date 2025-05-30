package com.scoreit.scoreit.api.music.spotify.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.scoreit.scoreit.api.music.spotify.dto.artist.ArtistSearchResponse;
import com.scoreit.scoreit.api.music.spotify.dto.artist.SeveralArtistsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ArtistSpotifyClient", url = "https://api.spotify.com")
public interface ArtistSpotifyClient {

    @GetMapping("/v1/search")
    ArtistSearchResponse searchArtist(
            @RequestHeader("Authorization") String authorization,
            @RequestParam("q") String query,
            @RequestParam("type") String type,
            @RequestParam("limit") Integer limit
    );
    @GetMapping("/v1/artists")
    SeveralArtistsResponse getSeveralArtists(
            @RequestHeader("Authorization") String authorization,
            @RequestParam("ids") String ids
    );

    @GetMapping("/v1/search")
    JsonNode searchArtists(
            @RequestHeader("Authorization") String authorization,
            @RequestParam("q") String query,
            @RequestParam("type") String type, // sempre "artist" para artistas
            @RequestParam("limit") Integer limit
    );

    @GetMapping("/v1/artists/{id}")
    JsonNode getArtist(
            @RequestHeader("Authorization") String authorization,
            @PathVariable("id") String artistId
    );

}