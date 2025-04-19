package com.scoreit.scoreit.api.music.spotify.dto.seach;

import java.util.List;

public record AlbumItem(
        String id,
        String name,
        List<Image> images,
        List<Artist> artists,
        String release_date
) {
}