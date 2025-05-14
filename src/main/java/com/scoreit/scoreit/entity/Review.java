package com.scoreit.scoreit.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "review")
public class Review {
    private Long id;
    private String media_id;
    private String media_type;
    private String member_id;
    private int score;
    private String member_review;
    private boolean spoiler;
    private LocalDateTime review_date;

    public Review(Long id, String media_id, String media_type, String member_id, int score, String member_review, boolean spoiler, LocalDateTime review_date) {
        this.id = id;
        this.media_id = media_id;
        this.media_type = media_type;
        this.member_id = member_id;
        this.score = score;
        this.member_review = member_review;
        this.spoiler = spoiler;
        this.review_date = review_date;
    }

    @PrePersist
    protected void onCreate() {
        this.review_date = LocalDateTime.now();
    }

    public LocalDateTime getReview_date() {
        return review_date;
    }

    public void setReview_date(LocalDateTime review_date) {
        this.review_date = review_date;
    }

    public boolean isSpoiler() {
        return spoiler;
    }

    public void setSpoiler(boolean spoiler) {
        this.spoiler = spoiler;
    }

    public String getMember_review() {
        return member_review;
    }

    public void setMember_review(String member_review) {
        this.member_review = member_review;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getMedia_type() {
        return media_type;
    }

    public void setMedia_type(String media_type) {
        this.media_type = media_type;
    }

    public String getMedia_id() {
        return media_id;
    }

    public void setMedia_id(String media_id) {
        this.media_id = media_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
