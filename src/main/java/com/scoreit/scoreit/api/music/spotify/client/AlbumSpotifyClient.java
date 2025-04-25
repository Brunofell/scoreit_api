package com.scoreit.scoreit.api.music.spotify.client;

import com.scoreit.scoreit.api.music.spotify.dto.album.AlbumResponseById;
import com.scoreit.scoreit.api.music.spotify.dto.album.AlbumResponse;
import com.scoreit.scoreit.api.music.spotify.dto.album.SeveralAlbumsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "AlbumSpotifyClient", url = "https://api.spotify.com")
public interface AlbumSpotifyClient {

    @GetMapping(value = "/v1/browse/new-releases")
    AlbumResponse getAlbumReleases(
            @RequestHeader("Authorization") String authorization,
            @RequestParam(value = "country", required = false) String country,
            @RequestParam(value = "limit", required = false) Integer limit,
            @RequestParam(value = "offset", required = false) Integer offset
    );

    @GetMapping(value = "/v1/albums/{id}")
    AlbumResponseById getAlbum(
            @RequestHeader("Authorization") String authorization,
            @PathVariable("id") String albumId
    );
    @GetMapping("/v1/search")
    AlbumResponse searchAlbum(
            @RequestHeader("Authorization") String authorization,
            @RequestParam("q") String query,
            @RequestParam("type") String type,
            @RequestParam("limit") Integer limit
    );

    @GetMapping("/v1/albums")
    SeveralAlbumsResponse getSeveralAlbums(
            @RequestHeader("Authorization") String authorization,
            @RequestParam("ids") String ids
    );



}
