//package com.scoreit.scoreit.dto.spotify;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/spotify")
//public class SpotifyController {
//
//    @Autowired
//    private SpotifyService spotifyService;
//
//    @GetMapping("/albums")
//    public SpotifyAlbumResponse getTrendingAlbums(@RequestParam(defaultValue = "1") int page) {
//        return spotifyService.getTrendingAlbums(page);
//    }
//
//
//}