package com.scoreit.scoreit.controller;

import com.scoreit.scoreit.dto.music.Artist;
import com.scoreit.scoreit.dto.music.ArtistResponse;
import com.scoreit.scoreit.service.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/music")
public class MusicController {

    @Autowired
    private MusicService musicService;


    @GetMapping("/artist/{name}")
    public Artist getArtist(@PathVariable String name) {
        ArtistResponse response = musicService.getArtist(name);
        return response.artist();
    }


}
