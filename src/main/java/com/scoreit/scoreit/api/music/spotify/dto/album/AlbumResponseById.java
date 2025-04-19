package com.scoreit.scoreit.api.music.spotify.dto.album;

import java.util.List;

public class AlbumResponseById {

    private String album_type;
    private int total_tracks;
    private List<String> available_markets;
    private ExternalUrls external_urls;
    private String href;
    private String id;
    private List<Image> images;
    private String name;
    private String release_date;
    private String release_date_precision;
    private String type;
    private String uri;
    private List<Artist> artists;
    private Tracks tracks;

    // Getters and Setters
    public String getAlbum_type() {
        return album_type;
    }

    public void setAlbum_type(String album_type) {
        this.album_type = album_type;
    }

    public int getTotal_tracks() {
        return total_tracks;
    }

    public void setTotal_tracks(int total_tracks) {
        this.total_tracks = total_tracks;
    }

    public List<String> getAvailable_markets() {
        return available_markets;
    }

    public void setAvailable_markets(List<String> available_markets) {
        this.available_markets = available_markets;
    }

    public ExternalUrls getExternal_urls() {
        return external_urls;
    }

    public void setExternal_urls(ExternalUrls external_urls) {
        this.external_urls = external_urls;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getRelease_date_precision() {
        return release_date_precision;
    }

    public void setRelease_date_precision(String release_date_precision) {
        this.release_date_precision = release_date_precision;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }

    public Tracks getTracks() {
        return tracks;
    }

    public void setTracks(Tracks tracks) {
        this.tracks = tracks;
    }
}

class ExternalUrls {
    private String spotify;

    // Getters and Setters
    public String getSpotify() {
        return spotify;
    }

    public void setSpotify(String spotify) {
        this.spotify = spotify;
    }
}

class Image {
    private String url;
    private int height;
    private int width;

    // Getters and Setters
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}

class Artist {
    private String name;
    private String uri;

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}

class Tracks {
    private String href;
    private int total;
    private List<Track> items;

    // Getters and Setters
    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Track> getItems() {
        return items;
    }

    public void setItems(List<Track> items) {
        this.items = items;
    }
}

class Track {
    private String name;
    private String uri;
    private int track_number;

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public int getTrack_number() {
        return track_number;
    }

    public void setTrack_number(int track_number) {
        this.track_number = track_number;
    }
}
