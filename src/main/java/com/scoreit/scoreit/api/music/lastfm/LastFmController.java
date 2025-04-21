package com.scoreit.scoreit.api.music.lastfm;

import com.scoreit.scoreit.api.music.UnifiedAlbum;
import com.scoreit.scoreit.api.music.UnifiedArtistResponse;

import com.scoreit.scoreit.api.music.lastfm.dto.album.Album;
import com.scoreit.scoreit.api.music.lastfm.dto.album.AlbumResponse;
import com.scoreit.scoreit.api.music.lastfm.dto.artist.UnifiedTopArtist;
import com.scoreit.scoreit.api.music.spotify.service.ArtistSpotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lastfm")
public class LastFmController {

    @Autowired
    private LastFmService lastFmService;
    @Autowired
    private ArtistSpotifyService artistSpotifyService;


    @GetMapping("/album/{tag}")
    public List<Album> getAlbum(
            @PathVariable String tag,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit
    ) {
        AlbumResponse response = lastFmService.getAlbum(tag, page, limit);
        return response.albums().album();
    }

    @GetMapping("/top-artists")
    public List<UnifiedTopArtist> getTopArtists(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit
    ) {
        return lastFmService.getTopArtists(page, limit);
    }

    @GetMapping("/albums-by-genre/{tag}")
    public List<UnifiedAlbum> getAlbumsByGenre(
            @PathVariable String tag,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit
    ) {
        AlbumResponse lastFmResponse = lastFmService.getAlbum(tag, page, limit);
        List<Album> albums = lastFmResponse.albums().album();

        return albums.stream().map(album -> {
            ResponseEntity<?> response = artistSpotifyService.getAlbumInfo(album.name(), album.artist().name());

            if (response.getBody() instanceof UnifiedAlbum unifiedAlbum) {
                return unifiedAlbum;
            }

            return new UnifiedAlbum(album.name(), null, null, album.artist().name());
        }).toList();
    }

/**/
}
