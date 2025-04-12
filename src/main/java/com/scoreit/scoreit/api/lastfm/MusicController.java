package com.scoreit.scoreit.api.lastfm;

import com.scoreit.scoreit.api.lastfm.dto.album.Album;
import com.scoreit.scoreit.api.lastfm.dto.album.AlbumResponse;
import com.scoreit.scoreit.api.lastfm.dto.artist.Artist;
import com.scoreit.scoreit.api.lastfm.dto.artist.ArtistResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/album/{tag}")
    public List<Album> getAlbum(
            @PathVariable String tag,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit
    ) {
        AlbumResponse response = musicService.getAlbum(tag, page, limit);
        return response.albums().album();
    }

}
