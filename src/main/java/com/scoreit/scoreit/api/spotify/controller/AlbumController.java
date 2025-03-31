package com.scoreit.scoreit.api.spotify.controller;

import com.scoreit.scoreit.api.spotify.dto.album.Album;
import com.scoreit.scoreit.api.spotify.dto.oauth.LoginRequest;
import com.scoreit.scoreit.api.spotify.client.AlbumSpotifyClient;
import com.scoreit.scoreit.api.spotify.client.AuthSpotifyClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/spotify/api")
public class AlbumController {

    @Autowired
    private AuthSpotifyClient authSpotifyClient;
    @Autowired
    private AlbumSpotifyClient albumSpotifyClient;


    @GetMapping("/oauth")
    public ResponseEntity<String> hello(){
        var request = new LoginRequest("client_credentials", "46f327a02d944095a28863edd7446a50", "3debe93c67ed487a841689865e56ac18");
        return ResponseEntity.ok(authSpotifyClient.login(request).getAccess_token());
    }

    @GetMapping("/albums")
    public ResponseEntity<List<Album>> getAlbumReleases(
            @RequestParam(value = "country", defaultValue = "US") String country,
            @RequestParam(value = "limit", defaultValue = "10") int limit,
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "sort", defaultValue = "desc") String sortOrder
    ){
        var request = new LoginRequest(
                "client_credentials",
                "46f327a02d944095a28863edd7446a50",
                "3debe93c67ed487a841689865e56ac18");
        var token = authSpotifyClient.login(request).getAccess_token();

        var response = albumSpotifyClient.getAlbumReleases("Bearer " + token, country, limit, offset);

        // Ordenação local
        List<Album> albums = response.getalbums().getItems();
        if ("asc".equalsIgnoreCase(sortOrder)) {
            albums.sort((a1, a2) -> a1.getRelease_date().compareTo(a2.getRelease_date())); // Mais velho para mais novo
        } else {
            albums.sort((a1, a2) -> a2.getRelease_date().compareTo(a1.getRelease_date())); // Mais novo para mais velho
        }

        // return ResponseEntity.ok(response.getalbums().getItems());
        return ResponseEntity.ok(albums);

    }
}













