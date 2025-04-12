package com.scoreit.scoreit.api.lastfm;

import com.scoreit.scoreit.api.lastfm.dto.album.AlbumResponse;
import com.scoreit.scoreit.api.lastfm.dto.artist.ArtistResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MusicService {
    private String apiKey = "087aa00c663e12673423be8af50c7ad0";
    private String baseUrl = "http://ws.audioscrobbler.com/2.0/";
    private RestTemplate restTemplate;

    public MusicService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ArtistResponse getArtist(String artistName) {
        String url = String.format("%s?method=artist.getinfo&artist=%s&api_key=%s&format=json",
                baseUrl, artistName, apiKey);
        return restTemplate.getForObject(url, ArtistResponse.class);
    }

    public AlbumResponse getAlbum(String tag, int page, int limit) {
        String url = String.format("%s?method=tag.gettopalbums&tag=%s&api_key=%s&format=json&page=%d&limit=%d",
                baseUrl, tag, apiKey, page, limit);
        return restTemplate.getForObject(url, AlbumResponse.class);
    }


}
