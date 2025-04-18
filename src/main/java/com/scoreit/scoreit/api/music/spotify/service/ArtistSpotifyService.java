package com.scoreit.scoreit.api.music.spotify.service;


import com.scoreit.scoreit.api.music.UnifiedAlbum;
import com.scoreit.scoreit.api.music.spotify.client.AlbumSpotifyClient;
import com.scoreit.scoreit.api.music.spotify.client.ArtistSpotifyClient;
import com.scoreit.scoreit.api.music.spotify.client.AuthSpotifyClient;
import com.scoreit.scoreit.api.music.spotify.dto.album.AlbumSpotifyInfo;
import com.scoreit.scoreit.api.music.spotify.dto.artist.Artist;
import com.scoreit.scoreit.api.music.spotify.dto.artist.ArtistImageResponse;
import com.scoreit.scoreit.api.music.spotify.dto.oauth.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class ArtistSpotifyService {
    @Autowired
    private AuthSpotifyClient authSpotifyClient;

    @Autowired
    private ArtistSpotifyClient artistSpotifyClient;
    @Autowired
    private AlbumSpotifyClient albumSpotifyClient;

    @GetMapping("/artist-image")
    public ResponseEntity<?> getArtistImage(@RequestParam("name") String artistName) {
        var loginRequest = new LoginRequest(
                "client_credentials",
                "46f327a02d944095a28863edd7446a50",
                "3debe93c67ed487a841689865e56ac18"
        );
        var token = authSpotifyClient.login(loginRequest).getAccess_token();

        var response = artistSpotifyClient.searchArtist("Bearer " + token, artistName, "artist", 1);
        var artists = response.getArtists().getItems();

        if (artists.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Artist artist = artists.get(0);
        String imageUrl = artist.getImages().isEmpty() ? null : artist.getImages().get(0).getUrl();

        return ResponseEntity.ok().body(new ArtistImageResponse(artist.getName(), artist.getId(), imageUrl));
    }

}
