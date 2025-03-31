package com.scoreit.scoreit.api.spotify.dto.album;

public class AlbumImage {
    private String url;

    public AlbumImage(String url) {
        this.url = url;
    }
    public AlbumImage() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
