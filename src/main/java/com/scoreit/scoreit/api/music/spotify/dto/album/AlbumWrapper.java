package com.scoreit.scoreit.api.music.spotify.dto.album;

import java.util.List;

public class AlbumWrapper {
    private List<Album> items;

    public AlbumWrapper(List<Album> items) {
        this.items = items;
    }

    public AlbumWrapper() {
    }

    public List<Album> getItems() {
        return items;
    }

    public void setItems(List<Album> items) {
        this.items = items;
    }

}
