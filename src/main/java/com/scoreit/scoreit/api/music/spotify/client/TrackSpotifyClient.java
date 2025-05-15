package com.scoreit.scoreit.api.music.spotify.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.scoreit.scoreit.api.music.spotify.dto.album.AlbumResponseById;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "TrackSpotifyClient", url = "https://api.spotify.com")
public interface TrackSpotifyClient {

    @GetMapping("/v1/search")
    JsonNode searchTrack(
            @RequestHeader("Authorization") String authorization,
            @RequestParam("q") String query,
            @RequestParam("type") String type,
            @RequestParam("limit") Integer limit
    );

    @GetMapping(value = "/v1/tracks/{id}")
    JsonNode getTrack(
            @RequestHeader("Authorization") String authorization,
            @PathVariable("id") String trackId
    );

}
