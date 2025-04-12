package com.scoreit.scoreit.api.music.spotify.client;

import com.scoreit.scoreit.api.music.spotify.dto.album.AlbumResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "AlbumSpotifyClient", url = "https://api.spotify.com")
public interface AlbumSpotifyClient {

    @GetMapping(value = "/v1/browse/new-releases")
    AlbumResponse getAlbumReleases(
            @RequestHeader("Authorization") String authorization,
            @RequestParam(value = "country", required = false) String country,
            @RequestParam(value = "limit", required = false) Integer limit,
            @RequestParam(value = "offset", required = false) Integer offset
    );
}
