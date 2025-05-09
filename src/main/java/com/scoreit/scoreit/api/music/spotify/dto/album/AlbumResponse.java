package com.scoreit.scoreit.api.music.spotify.dto.album;

public class AlbumResponse {
    private AlbumWrapper albums;

    public AlbumResponse(AlbumWrapper albums) {
        this.albums = albums;
    }

    public AlbumResponse() {
    }

    public AlbumWrapper getAlbums() {
        return albums;
    }

    public void setAlbuns(AlbumWrapper albuns) {
        this.albums = albums;
    }
}
