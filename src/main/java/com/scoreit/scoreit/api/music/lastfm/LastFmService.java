package com.scoreit.scoreit.api.music.lastfm;

import com.scoreit.scoreit.api.music.UnifiedArtistResponse;

import com.scoreit.scoreit.api.music.lastfm.dto.artist.*;
import com.scoreit.scoreit.api.music.spotify.dto.artist.ArtistImageResponse;
import com.scoreit.scoreit.api.music.lastfm.dto.album.AlbumResponse;
import com.scoreit.scoreit.api.music.spotify.service.ArtistSpotifyService;
import feign.template.UriUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class LastFmService {
    private String apiKey = "087aa00c663e12673423be8af50c7ad0";
    private String baseUrl = "http://ws.audioscrobbler.com/2.0/";
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ArtistSpotifyService artistSpotifyService;


    public LastFmArtistResponse getArtist(String artistName) {
        String url = String.format("%s?method=artist.getinfo&artist=%s&api_key=%s&format=json",
                baseUrl, artistName, apiKey);
        System.out.println(artistSpotifyService.getArtistImage(artistName));
        return restTemplate.getForObject(url, LastFmArtistResponse.class);
    }

    public AlbumResponse getAlbum(String tag, int page, int limit) {
        String url = String.format("%s?method=tag.gettopalbums&tag=%s&api_key=%s&format=json&page=%d&limit=%d",
                baseUrl, tag, apiKey, page, limit);
        return restTemplate.getForObject(url, AlbumResponse.class);
    }

    public List<UnifiedTopArtist> getTopArtists(int page, int limit) {
        String url = String.format("%s?method=chart.gettopartists&api_key=%s&format=json&page=%d&limit=%d",
                baseUrl, apiKey, page, limit);

        TopArtistsResponse response = restTemplate.getForObject(url, TopArtistsResponse.class);

        return response.artists().artist().stream().map(artist -> {
            String spotifyImage = null;

            ResponseEntity<?> spotifyResponse = artistSpotifyService.getArtistImage(artist.name());
            String id = null;
            if (spotifyResponse.getBody() instanceof ArtistImageResponse imageResponse) {
                spotifyImage = imageResponse.imageUrl();
                id = imageResponse.id();
            }

            return new UnifiedTopArtist(
                    artist.name(),
                    spotifyImage,
                    id,
                    artist.playcount(),
                    artist.listeners(),
                    artist.mbid()
            );
        }).toList();
    }


}
