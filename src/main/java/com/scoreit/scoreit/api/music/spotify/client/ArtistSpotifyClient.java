package com.scoreit.scoreit.api.music.spotify.client;

import com.scoreit.scoreit.api.music.spotify.dto.artist.ArtistSearchResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
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
}