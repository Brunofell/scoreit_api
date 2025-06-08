package com.scoreit.scoreit.service;

import com.scoreit.scoreit.api.music.spotify.client.AlbumSpotifyClient;
import com.scoreit.scoreit.api.music.spotify.client.AuthSpotifyClient;
import com.scoreit.scoreit.api.music.spotify.dto.album.Album;
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

    public FeedService(MemberService memberService, ReviewService reviewService, MovieService movieService, SeriesService seriesService, AlbumSpotifyClient albumSpotifyClient, AuthSpotifyClient authSpotifyClient){
        this.memberService = memberService;
        this.reviewService = reviewService;
        this.movieService = movieService;
        this.seriesService = seriesService;
        this.albumSpotifyClient = albumSpotifyClient;
        this.authSpotifyClient = authSpotifyClient;
    }

    public List<FeedResponse> montaFeed(Long id){
        List<FeedResponse> feed = new ArrayList<>();
        List<ReviewResponse> reviews = reviewService.getReviewsFromFollowedMembers(id);
        for(ReviewResponse reviewResponse : reviews){
            FeedResponse feedItem = new FeedResponse();

            feedItem.setReview(reviewResponse);

            preencheMembro(reviewResponse, feedItem);

            preencheMidia(reviewResponse, feedItem);

            feed.add(feedItem);
        }

        return feed;
    }

    // Dentro da classe FeedService

    private void preencheMidia(ReviewResponse reviewResponse, FeedResponse feedItem) {
        try {
            if ("movie".equals(reviewResponse.mediaType())) {
                System.out.println("Buscando filme com ID: " + reviewResponse.mediaId());
                Movie filme = movieService.getMovieById(Integer.parseInt(reviewResponse.mediaId()));
                feedItem.setMovie(filme);
                System.out.println("Filme encontrado com sucesso.");
            }
            if ("series".equals(reviewResponse.mediaType())) {
                System.out.println("Buscando série com ID: " + reviewResponse.mediaId());
                Series series = seriesService.getSeriesById(Integer.parseInt(reviewResponse.mediaId()));
                feedItem.setSerie(series);
                System.out.println("Série encontrada com sucesso.");
            }
            if ("album".equals(reviewResponse.mediaType())) {
                System.out.println("Buscando álbum com ID: " + reviewResponse.mediaId());
                preencheAlbum(reviewResponse, feedItem);
                System.out.println("Álbum encontrado com sucesso.");
            }
        } catch (Exception e) {
            // Este bloco vai capturar a exceção da API externa!
            System.err.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            System.err.println("ERRO AO BUSCAR MÍDIA EXTERNA! TIPO: " + reviewResponse.mediaType() + ", ID: " + reviewResponse.mediaId());
            System.err.println("Mensagem da Exceção: " + e.getMessage());

            // Se você usa Feign, a linha abaixo é extremamente útil, pois mostra a resposta da API
             if (e instanceof feign.FeignException) {
                 System.err.println("Corpo da resposta de erro da API: " + ((feign.FeignException) e).contentUTF8());
             }

            System.err.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

            // Você pode decidir lançar a exceção de novo ou simplesmente pular este item do feed
            // Por enquanto, vamos lançar para manter o comportamento de erro
            throw e;
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
        Member membro = membroOptional.get();
        MemberResponse memberResponse = new MemberResponse(
                membro.getId(),
                membro.getUsername(),
                membro.getBirthDate(),
                membro.getProfileImageUrl(),
                membro.getGender(),
                membro.getBio()
        );

        feedItem.setMember(memberResponse); // Seta o DTO, não a entidade
        feedItem.setMember(memberResponse);
    }

}
