package com.scoreit.scoreit.dto;


import com.scoreit.scoreit.api.music.spotify.dto.album.AlbumResponseById;
import com.scoreit.scoreit.api.tmdb.movie.dto.Movie;
import com.scoreit.scoreit.api.tmdb.series.dto.Series;
import com.scoreit.scoreit.dto.feed.FeedResponse;
import com.scoreit.scoreit.dto.member.Gender;
import com.scoreit.scoreit.dto.member.MemberResponse;
import com.scoreit.scoreit.dto.review.ReviewResponse;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class FeedResponseTest {

    @Test
    void shouldSetAndGetAllFields() {
        FeedResponse response = new FeedResponse();

        MemberResponse member = new MemberResponse(
                1L,
                "User Name",
                "userhandle",
                LocalDate.of(1990, 1, 1),
                "https://example.com/profile.jpg",
                Gender.MASC,
                "Bio do usuário"
        );

        // ReviewResponse conforme seu record
        ReviewResponse review = new ReviewResponse(
                10L,                  // id
                "M123",               // mediaId
                "MOVIE",              // mediaType
                1L,                   // memberId
                5,                    // score
                "Ótimo!",             // memberReview
                LocalDate.of(2025, 11, 1), // watchDate
                false,                // spoiler
                LocalDateTime.now()   // reviewDate
        );

        // Movie conforme seu record
        Movie movie = new Movie(
                123,                     // id
                "Movie Test",            // title
                null,                    // overview
                null,                    // poster_path
                null,                    // backdrop_path
                8.5,                     // vote_average
                null,                    // release_date
                Collections.emptyList(), // genre_ids
                Collections.emptyList()  // genres
        );

        // Series conforme seu record
        Series serie = new Series(
                456,                     // id
                "Series Test",           // name
                null,                    // overview
                null,                    // poster_path
                null,                    // backdrop_path
                9.0,                     // vote_average
                null,                    // release_date
                Collections.emptyList(), // seasons
                Collections.emptyList(), // genres
                Collections.emptyList(), // genre_ids
                null                     // content_rating
        );

        response.setMember(member);
        response.setReview(review);
        response.setMovie(movie);
        response.setSerie(serie);

        assertEquals(member, response.getMember());
        assertEquals(review, response.getReview());
        assertEquals(movie, response.getMovie());
        assertEquals(serie, response.getSerie());
    }
}
