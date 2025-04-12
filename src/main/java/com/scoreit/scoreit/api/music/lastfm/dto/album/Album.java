package com.scoreit.scoreit.api.music.lastfm.dto.album;


import com.scoreit.scoreit.api.music.lastfm.dto.Image;
import com.scoreit.scoreit.api.music.lastfm.dto.artist.LastFmArtist;

public record Album(
        String name,
        Image[] image,
        LastFmArtist lastFmArtist
) {

}
