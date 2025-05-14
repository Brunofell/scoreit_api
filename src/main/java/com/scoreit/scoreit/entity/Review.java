package com.scoreit.scoreit.entity;

import com.scoreit.scoreit.dto.review.ReviewRegister;
import jakarta.persistence.*;

import java.time.LocalDateTime;
@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "media_id")
    private String mediaId;
    @Column(name = "media_type")
    private String mediaType;
    @Column(name = "member_id")
    private String memberId;
    private int score;
    @Column(name = "member_review")
    private String memberReview;
    private boolean spoiler;
    @Column(name = "review_date")
    private LocalDateTime reviewDate;

    public Review(){}

    public Review(Long id, String mediaId, String mediaType, String memberId, int score, String memberReview, boolean spoiler, LocalDateTime reviewDate) {
        this.id = id;
        this.mediaId = mediaId;
        this.mediaType = mediaType;
        this.memberId = memberId;
        this.score = score;
        this.memberReview = memberReview;
        this.spoiler = spoiler;
        this.reviewDate = reviewDate;
    }

    public Review(ReviewRegister data) {
        this.mediaId = data.mediaId();
        this.mediaType = data.mediaType();
        this.memberId = data.memberId();
        this.score = data.score();
        this.memberReview = data.memberReview();
        this.spoiler = data.spoiler();
    }

    @PrePersist
    protected void onCreate() {
        this.reviewDate = LocalDateTime.now();
    }

    public LocalDateTime getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(LocalDateTime reviewDate) {
        this.reviewDate = reviewDate;
    }

    public boolean isSpoiler() {
        return spoiler;
    }

    public void setSpoiler(boolean spoiler) {
        this.spoiler = spoiler;
    }

    public String getMemberReview() {
        return memberReview;
    }

    public void setMemberReview(String memberReview) {
        this.memberReview = memberReview;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
