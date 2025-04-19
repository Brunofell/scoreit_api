package com.scoreit.scoreit.api.music.spotify.dto.seach;

import java.util.List;

public record Albums(
        List<AlbumItem> items
) {
}