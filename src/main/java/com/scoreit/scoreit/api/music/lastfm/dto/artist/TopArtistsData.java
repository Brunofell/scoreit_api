package com.scoreit.scoreit.api.music.lastfm.dto.artist;


import java.util.List;

public record TopArtistsData(
        List<LastFmArtist> artist
) {}