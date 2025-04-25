package com.scoreit.scoreit.api.music.spotify.dto.artist;

import java.util.List;

public record SeveralArtistsResponse(
        List<ArtistDTO> artists
) {}