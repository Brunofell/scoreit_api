package com.scoreit.scoreit.entity;

import com.scoreit.scoreit.dto.review.MediaType;
import com.scoreit.scoreit.dto.review.ReviewRegister;
import com.scoreit.scoreit.dto.review.ReviewUpdate;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "media_id", nullable = false)
    private String mediaId;

    @Enumerated(EnumType.STRING)
    @Column(name = "media_type", nullable = false, length = 20)
    private MediaType mediaType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    private int score;

    @Column(name = "member_review", length = 260)
    private String memberReview;

    @Column(name = "watch_date", nullable = false)
    private LocalDate watchDate;

    @Column(nullable = false)
    private boolean spoiler;

    @Column(name = "review_date", nullable = false)
    private LocalDateTime reviewDate;

    public Review() {}

    public Review(Long id, String mediaId, MediaType mediaType, Member member, int score,
                  String memberReview, LocalDate watchDate, boolean spoiler, LocalDateTime reviewDate) {
        this.id = id;
        this.mediaId = mediaId;
        this.mediaType = mediaType;
        this.member = member;
        this.score = score;
        this.memberReview = memberReview;
        this.watchDate = watchDate;
        this.spoiler = spoiler;
        this.reviewDate = reviewDate;
    }

    public Review(ReviewRegister data, Member member) {
        this.mediaId = data.mediaId();
        this.mediaType = data.mediaType();
        this.member = member;
        this.score = data.score();
        this.watchDate = data.watchDate();
        this.memberReview = data.memberReview();
        this.spoiler = data.spoiler();
    }

    @PrePersist
    protected void onCreate() {
        this.reviewDate = LocalDateTime.now();
    }

    public void updateInfos(ReviewUpdate data) {
        if(data.score() != null){
            this.score = data.score();
        }
        if(data.watchDate() != null){
            this.watchDate = data.watchDate();
        }
        if(data.spoiler() != null){
            this.spoiler = data.spoiler();
        }
        if(data.memberReview() != null){
            this.memberReview = data.memberReview();
        }
    }

    public LocalDate getWatchDate() { return watchDate; }
    public void setWatchDate(LocalDate watchDate) { this.watchDate = watchDate; }

    public LocalDateTime getReviewDate() { return reviewDate; }
    public void setReviewDate(LocalDateTime reviewDate) { this.reviewDate = reviewDate; }

    public boolean isSpoiler() { return spoiler; }
    public void setSpoiler(boolean spoiler) { this.spoiler = spoiler; }

    public String getMemberReview() { return memberReview; }
    public void setMemberReview(String memberReview) { this.memberReview = memberReview; }

    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }

    public Member getMember() { return member; }
    public void setMember(Member member) { this.member = member; }

    public MediaType getMediaType() { return mediaType; }
    public void setMediaType(MediaType mediaType) { this.mediaType = mediaType; }

    public String getMediaId() { return mediaId; }
    public void setMediaId(String mediaId) { this.mediaId = mediaId; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
}
