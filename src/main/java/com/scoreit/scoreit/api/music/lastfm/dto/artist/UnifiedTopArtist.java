package com.scoreit.scoreit.api.music.lastfm.dto.artist;

public record UnifiedTopArtist(
        String name,
        String imageUrl,
        String id,
        String playcount,
        String listeners,
        String mbid
) {}