package com.scoreit.scoreit.dto.spotify;

public class Album {
    private String id;
    private String name;
    private String release_date;


    public Album(String release_date, String name, String id) {
        this.release_date = release_date;
        this.name = name;
        this.id = id;
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
}
