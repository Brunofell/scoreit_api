package com.scoreit.scoreit.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "favorite_list_content")
public class FavoriteListContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "favorite_list_id")
    private FavoriteList favoriteList;

    @Column(name = "media_id")
    private String mediaId;

    @Column(name = "media_type")
    private String mediaType;

    public FavoriteListContent(Long id, FavoriteList favoriteList, String mediaId, String mediaType) {
        this.id = id;
        this.favoriteList = favoriteList;
        this.mediaId = mediaId;
        this.mediaType = mediaType;
    }

    public FavoriteListContent() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FavoriteList getCustomList() {
        return favoriteList;
    }

    public void setCustomList(FavoriteList favoriteList) {
        this.favoriteList = favoriteList;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }
}
