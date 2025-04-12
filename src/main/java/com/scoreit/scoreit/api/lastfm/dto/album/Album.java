package com.scoreit.scoreit.api.lastfm.dto.album;

import com.scoreit.scoreit.api.lastfm.dto.artist.Artist;
import com.scoreit.scoreit.api.lastfm.dto.Image;

public record Album(
        String name,
        Image[] image,
        Artist artist
) {

}
