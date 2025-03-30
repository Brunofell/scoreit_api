package com.scoreit.scoreit.dto.spotify;

public class AlbumResponse {
    private AlbumWrapper albums;

    public AlbumResponse(AlbumWrapper albums) {
        this.albums = albums;
    }

    public AlbumResponse() {
    }

    public AlbumWrapper getalbums() {
        return albums;
    }

    public void setAlbuns(AlbumWrapper albuns) {
        this.albums = albums;
    }
}
