package com.scoreit.scoreit.api.music.spotify.dto.album;

import java.util.List;

public record SeveralAlbumsResponse(
        List<AlbumResponseById> albums
) {}