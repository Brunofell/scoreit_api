package com.scoreit.scoreit.api.music.lastfm.dto.track;

public class Track {
    private String name;
    private String url;
    private int duration;
    private Artist artist;

    public Track() {
    }

    public Track(String name, String url, int duration, Artist artist) {
        this.name = name;
        this.url = url;
        this.duration = duration;
        this.artist = artist;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public int getDuration() {
        return duration;
    }

    public Artist getArtist() {
        return artist;
    }

    public static class Artist {
        private String name;

        public Artist() {
        }

        public Artist(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}