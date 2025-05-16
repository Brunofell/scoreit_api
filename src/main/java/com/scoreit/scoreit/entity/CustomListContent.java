package com.scoreit.scoreit.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "custom_list_content")
public class CustomListContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "custom_list_id")
    private CustomList customList;
    @Column(name = "media_id")
    private String mediaId;
    @Column(name = "media_type")
    private String mediaType;

    public CustomListContent(){

    }

    public CustomListContent(Long id, String mediaType, String mediaId, CustomList customList) {
        this.id = id;
        this.mediaType = mediaType;
        this.mediaId = mediaId;
        this.customList = customList;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public CustomList getCustomList() {
        return customList;
    }

    public void setCustomList(CustomList customList) {
        this.customList = customList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
