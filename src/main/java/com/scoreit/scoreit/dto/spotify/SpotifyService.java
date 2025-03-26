//package com.scoreit.scoreit.dto.spotify;
//
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//@Service
//public class SpotifyService {
//    private String accessToken = "BQAEwgfEecl7CoTwFtE5BMR4A5u-hMfPBBK62QJQWqzLNSOeIDc6WgsBXm8GQQicsSwwP7vn19lTUJFTb8_dNwRczisxk4qGwhSrxe6xl5oOGLSyL1t58nFEjONHX4g0Bc8u3vlECb52b6of8Gl5ulXZ7apleg1cODddGc0Bgmk1B7kM0t1IhhMAYEau3qLF2M3LpMEjBFP2ZKofSbyH8HYoflOSD9hXio2sPw\n";  // Substitua pelo seu token de acesso
//    private String baseUrl = "https://api.spotify.com/v1";
//    private RestTemplate restTemplate;
//
//
//    public SpotifyService(RestTemplate restTemplate) {
//        this.restTemplate = restTemplate;
//    }
//
//    public SpotifyAlbumResponse getTrendingAlbums(int page) {
//        String url = String.format("%s/browse/featured-playlists?limit=20&offset=%d", baseUrl, (page - 1) * 20);
//        return restTemplate.getForObject(url, SpotifyAlbumResponse.class);
//    }
//
//
//}