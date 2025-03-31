package com.scoreit.scoreit.api.spotify.dto.album;

public class AlbumArtist {
    private String id;
    private String name;

    public AlbumArtist(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public AlbumArtist() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
