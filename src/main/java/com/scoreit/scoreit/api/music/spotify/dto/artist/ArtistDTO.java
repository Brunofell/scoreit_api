package com.scoreit.scoreit.api.music.spotify.dto.artist;


import java.util.List;

public record ArtistDTO(
        String id,
        String name,
        List<ImageDTO> images
) {}