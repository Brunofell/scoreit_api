package com.scoreit.scoreit.entity;

import com.scoreit.scoreit.dto.review.ReviewRegister;
import com.scoreit.scoreit.dto.review.ReviewUpdate;
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

    // Trocar String memberId por relacionamento com Member
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    private int score;

    @Column(name = "member_review")
    private String memberReview;

    @Column(name = "watch_date")
    private LocalDateTime watchDate;

    private boolean spoiler;

    @Column(name = "review_date")
    private LocalDateTime reviewDate;

    @Column(name = "genres")
    private String genres;

    public Review() {}

    // Ajuste construtor para receber Member
    public Review(Long id, String mediaId, String mediaType, Member member, int score, String memberReview, LocalDateTime watchDate ,boolean spoiler, LocalDateTime reviewDate, String genres) {
        this.id = id;
        this.mediaId = mediaId;
        this.mediaType = mediaType;
        this.member = member;
        this.score = score;
        this.memberReview = memberReview;
        this.watchDate = watchDate;
        this.spoiler = spoiler;
        this.reviewDate = reviewDate;
        this.genres = genres;
    }

    // Ajustar construtor que recebe ReviewRegister - agora precisa do Member no lugar do memberId String
    public Review(ReviewRegister data, Member member, String genres) {
        this.mediaId = data.mediaId();
        this.mediaType = data.mediaType();
        this.member = member;
        this.score = data.score();
        this.watchDate = data.watchDate();
        this.memberReview = data.memberReview();
        this.spoiler = data.spoiler();
        this.genres = genres;
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

    public LocalDateTime getWatchDate() {
        return watchDate;
    }

    public void setWatchDate(LocalDateTime watchDate) {
        this.watchDate = watchDate;
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

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
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

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }
}
