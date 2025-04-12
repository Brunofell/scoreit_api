package com.scoreit.scoreit.api.music.spotify.dto.album;

import java.util.List;

public class Album {
    private String id;
    private String name;
    private String release_date;
    private List<AlbumImage> images;
    private List<AlbumArtist> artists;


    public Album(String release_date, String name, String id, List<AlbumImage> images, List<AlbumArtist> artists) {
        this.release_date = release_date;
        this.name = name;
        this.id = id;
        this.images = images;
        this.artists = artists;
    }

    public Album() {
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<AlbumImage> getImages() {
        return images;
    }

    public void setImages(List<AlbumImage> images) {
        this.images = images;
    }

    public List<AlbumArtist> getArtists() {
        return artists;
    }

    public void setArtists(List<AlbumArtist> artists) {
        this.artists = artists;
    }
}
