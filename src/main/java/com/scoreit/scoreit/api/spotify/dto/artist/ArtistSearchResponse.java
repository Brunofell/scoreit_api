package com.scoreit.scoreit.api.spotify.dto.artist;


import java.util.List;

public class ArtistSearchResponse {
    private Artists artists;

    public Artists getArtists() {
        return artists;
    }

    public void setArtists(Artists artists) {
        this.artists = artists;
    }

    public static class Artists {
        private List<Artist> items;

        public List<Artist> getItems() {
            return items;
        }

        public void setItems(List<Artist> items) {
            this.items = items;
        }
    }
}

