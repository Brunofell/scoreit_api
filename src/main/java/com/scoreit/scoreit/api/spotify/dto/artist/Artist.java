package com.scoreit.scoreit.api.spotify.dto.artist;

import java.util.List;

public class Artist {
    private String id;
    private String name;
    private List<ArtistImage> images;

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

    public List<ArtistImage> getImages() {
        return images;
    }

    public void setImages(List<ArtistImage> images) {
        this.images = images;
    }
}