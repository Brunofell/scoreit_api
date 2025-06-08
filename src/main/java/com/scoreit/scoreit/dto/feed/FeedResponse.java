package com.scoreit.scoreit.dto.feed;

import com.scoreit.scoreit.api.music.spotify.dto.album.AlbumResponseById;
import com.scoreit.scoreit.api.tmdb.movie.dto.Movie;
import com.scoreit.scoreit.api.tmdb.series.dto.Series;
import com.scoreit.scoreit.dto.member.MemberResponse;
import com.scoreit.scoreit.dto.review.ReviewResponse;
import com.scoreit.scoreit.entity.Member;

public class FeedResponse {
    private MemberResponse member;
    private ReviewResponse review;
    private Movie movie;
    private Series serie;
    private AlbumResponseById album;

    public MemberResponse getMember() {
        return member;
    }

    public void setMember(MemberResponse member) {
        this.member = member;
    }

    public ReviewResponse getReview() {
        return review;
    }

    public void setReview(ReviewResponse review) {
        this.review = review;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public AlbumResponseById getAlbum() {
        return album;
    }

    public void setAlbum(AlbumResponseById album) {
        this.album = album;
    }

    public Series getSerie() {
        return serie;
    }

    public void setSerie(Series serie) {
        this.serie = serie;
    }
}
