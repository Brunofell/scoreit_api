package com.scoreit.scoreit.dto.spotify;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/spotify/api")
public class OauthController {

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
    public ResponseEntity<List<Album>> getAlbumReleases(){
        var request = new LoginRequest(
                "client_credentials",
                "46f327a02d944095a28863edd7446a50",
                "3debe93c67ed487a841689865e56ac18");
        var token = authSpotifyClient.login(request).getAccess_token();

        var response = albumSpotifyClient.getAlbumReleases("Bearer " + token);

        return ResponseEntity.ok(response.getalbums().getItems());
    }
}













