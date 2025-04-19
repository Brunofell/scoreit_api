package com.scoreit.scoreit.api.music.spotify.dto.seach;


import java.util.List;

public class SearchResponse {
    private Albums albums;

    public static class Albums {
        private List<Item> items;

        public List<Item> getItems() {
            return items;
        }

        public void setItems(List<Item> items) {
            this.items = items;
        }
    }

    public static class Item {
        private String name;
        private String id;
        private List<Artist> artists;
        private String release_date;
        private List<Image> images;

        // Getters and Setters

        public static class Artist {
            private String name;

            // Getters and Setters
        }

        public static class Image {
            private String url;

            // Getters and Setters
        }
    }

    public Albums getAlbums() {
        return albums;
    }

    public void setAlbums(Albums albums) {
        this.albums = albums;
    }
}
