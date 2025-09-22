package com.scoreit.scoreit.service;

import com.scoreit.scoreit.api.music.spotify.client.AlbumSpotifyClient;
import com.scoreit.scoreit.api.music.spotify.client.AuthSpotifyClient;
import com.scoreit.scoreit.api.music.spotify.dto.album.AlbumResponseById;
import com.scoreit.scoreit.api.music.spotify.dto.oauth.LoginRequest;
import com.scoreit.scoreit.api.tmdb.movie.dto.Movie;
import com.scoreit.scoreit.api.tmdb.movie.service.MovieService;
import com.scoreit.scoreit.api.tmdb.series.dto.Series;
import com.scoreit.scoreit.api.tmdb.series.service.SeriesService;
import com.scoreit.scoreit.dto.feed.FeedResponse;
import com.scoreit.scoreit.dto.member.MemberResponse;
import com.scoreit.scoreit.dto.review.ReviewResponse;
import com.scoreit.scoreit.entity.Member;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FeedService {

    private MemberService memberService;
    private ReviewService reviewService;
    private MovieService movieService;
    private SeriesService seriesService;
    private AlbumSpotifyClient albumSpotifyClient;
    private AuthSpotifyClient authSpotifyClient;

    public FeedService(MemberService memberService,
                       ReviewService reviewService,
                       MovieService movieService,
                       SeriesService seriesService,
                       AlbumSpotifyClient albumSpotifyClient,
                       AuthSpotifyClient authSpotifyClient) {
        this.memberService = memberService;
        this.reviewService = reviewService;
        this.movieService = movieService;
        this.seriesService = seriesService;
        this.albumSpotifyClient = albumSpotifyClient;
        this.authSpotifyClient = authSpotifyClient;
    }

    public List<FeedResponse> montaFeed(Long id, String language) {
        List<FeedResponse> feed = new ArrayList<>();
        List<ReviewResponse> reviews = reviewService.getReviewsFromFollowedMembers(id);
        for (ReviewResponse reviewResponse : reviews) {
            FeedResponse feedItem = new FeedResponse();

            feedItem.setReview(reviewResponse);

            preencheMembro(reviewResponse, feedItem);

            preencheMidia(reviewResponse, feedItem, language);

            feed.add(feedItem);
        }

        return feed;
    }

    private void preencheMidia(ReviewResponse reviewResponse, FeedResponse feedItem, String language) {
        try {
            switch (reviewResponse.mediaType().toLowerCase()) {
                case "movie" -> {
                    try {
                        int movieId = Integer.parseInt(reviewResponse.mediaId());
                        feedItem.setMovie(movieService.getMovieById(movieId, language));
                    } catch (Exception e) {
                        System.err.println("Erro ao buscar filme: " + e.getMessage());
                        feedItem.setMovie(null);
                    }
                }
                case "series" -> {
                    try {
                        int seriesId = Integer.parseInt(reviewResponse.mediaId());
                        feedItem.setSerie(seriesService.getSeriesById(seriesId, language));
                    } catch (Exception e) {
                        System.err.println("Erro ao buscar série: " + e.getMessage());
                        feedItem.setSerie(null);
                    }
                }
                case "album" -> {
                    try {
                        preencheAlbum(reviewResponse, feedItem);
                    } catch (Exception e) {
                        System.err.println("Erro ao buscar álbum: " + e.getMessage());
                        feedItem.setAlbum(null);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Erro genérico ao preencher mídia: " + e.getMessage());
        }
    }


    private void preencheAlbum(ReviewResponse reviewResponse, FeedResponse feedItem) {
        var request = new LoginRequest(
                "client_credentials",
                "46f327a02d944095a28863edd7446a50",
                "3debe93c67ed487a841689865e56ac18"
        );

        var token = authSpotifyClient.login(request).getAccess_token();
        System.out.println("Token de autorização: " + token);  // Verifique se o token está correto

        AlbumResponseById album = albumSpotifyClient.getAlbum("Bearer " + token, reviewResponse.mediaId());
        feedItem.setAlbum(album);
    }

    private void preencheMembro(ReviewResponse reviewResponse, FeedResponse feedItem) {
        Optional<Member> membroOptional = memberService.getMemberById(reviewResponse.memberId());

        // Tratar Optional de forma segura
        Member membro = membroOptional.orElseThrow(() ->
                new RuntimeException("Membro não encontrado: " + reviewResponse.memberId()));

        MemberResponse memberResponse = new MemberResponse(
                membro.getId(),
                membro.getName(),
                membro.getHandle(),
                membro.getBirthDate(),
                membro.getProfileImageUrl(),
                membro.getGender(),
                membro.getBio()
        );

        // Seta o DTO (apenas uma vez)
        feedItem.setMember(memberResponse);
    }

}
