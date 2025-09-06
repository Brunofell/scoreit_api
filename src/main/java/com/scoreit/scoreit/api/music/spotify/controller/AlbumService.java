package com.scoreit.scoreit.api.music.spotify.controller;

import com.scoreit.scoreit.api.music.spotify.client.AlbumSpotifyClient;
import com.scoreit.scoreit.api.music.spotify.client.AuthSpotifyClient;
import com.scoreit.scoreit.api.music.spotify.dto.album.AlbumResponseById;
import com.scoreit.scoreit.api.music.spotify.dto.oauth.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlbumService {

    @Autowired
    private AlbumSpotifyClient albumSpotifyClient;

    @Autowired
    private AuthSpotifyClient authSpotifyClient;

//    public String getAlbumGenres(String albumId) {
//        var request = new LoginRequest(
//                "client_credentials",
//                "CLIENT_ID",
//                "CLIENT_SECRET"
//        );
//
//        String token = authSpotifyClient.login(request).getAccess_token();
//
//        AlbumResponseById album = albumSpotifyClient.getAlbum("Bearer " + token, albumId);
//
//        if (album.getGenres() != null && !album.getGenres().isEmpty()) {
//            return String.join(",", album.getGenres());
//        }
//        return null;
//    }
}
