package com.scoreit.scoreit.service;

import com.scoreit.scoreit.dto.review.MediaType;
import com.scoreit.scoreit.entity.Review;
import com.scoreit.scoreit.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecommendationService {

    @Autowired
    private ReviewRepository reviewRepository;

    private final String TMDB_API_KEY = "4b6d83522f71b2da2b7b52c4386131e0";
    private final String TMDB_BASE_URL = "https://api.themoviedb.org/3";

    /**
     * Versão compatível com quem chama usando String
     */
    public List<Map<String, Object>> getRecommendations(Long memberId, String mediaType, String lang) {
        MediaType mt = MediaType.fromJson(mediaType); // converte para enum (case-insensitive)
        if (mt == null) {
            throw new IllegalArgumentException("MediaType inválido: " + mediaType);
        }
        return getRecommendations(memberId, mt, lang);
    }

    /**
     * Versão type-safe usando enum MediaType
     */
    public List<Map<String, Object>> getRecommendations(Long memberId, MediaType mediaType, String lang) {
        // pega os top 3 reviews mais bem avaliados do usuário para o tipo
        List<Review> topReviews = reviewRepository.findTopByMemberAndMediaType(
                memberId, mediaType, PageRequest.of(0, 3)
        );

        List<Map<String, Object>> recommendations = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();

        // 1. Carrega lista de gêneros do TMDB no idioma desejado
        Map<Integer, String> genreMap = fetchGenres(mediaType, lang, restTemplate);

        for (Review review : topReviews) {
            String url = buildTmdbUrl(review.getMediaId(), mediaType, lang);

            try {
                @SuppressWarnings("unchecked")
                Map<String, Object> response = restTemplate.getForObject(url, Map.class);

                if (response != null) {
                    @SuppressWarnings("unchecked")
                    List<Map<String, Object>> results = (List<Map<String, Object>>) response.getOrDefault("results", Collections.emptyList());

                    // Popula os nomes dos gêneros
                    results.forEach(movie -> {
                        @SuppressWarnings("unchecked")
                        List<Integer> genreIds = (List<Integer>) movie.getOrDefault("genre_ids", new ArrayList<>());
                        List<Map<String, Object>> genres = genreIds.stream()
                                .map(id -> {
                                    Map<String, Object> g = new HashMap<>();
                                    g.put("id", id);
                                    g.put("name", genreMap.getOrDefault(id, "Desconhecido"));
                                    return g;
                                })
                                .toList();
                        movie.put("genres", genres);
                        String genreNames = genres.stream()
                                .map(g -> (String) g.get("name"))
                                .collect(Collectors.joining(", "));
                        movie.put("genre", genreNames.isEmpty() ? "Desconhecido" : genreNames);
                    });

                    recommendations.addAll(results);
                }
            } catch (Exception e) {
                System.out.println("Erro ao chamar TMDB: " + e.getMessage());
            }
        }

        // remove duplicados pelo campo "id"
        return recommendations.stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.toMap(r -> r.get("id"), r -> r, (r1, r2) -> r1),
                        m -> new ArrayList<>(m.values())
                ));
    }

    /**
     * Monta a URL para chamada da API TMDB com o idioma dinâmico
     */
    private String buildTmdbUrl(String mediaId, MediaType mediaType, String lang) {
        switch (mediaType) {
            case MOVIE:
                return TMDB_BASE_URL + "/movie/" + mediaId + "/recommendations?api_key=" + TMDB_API_KEY + "&language=" + lang;
            case SERIES:
                return TMDB_BASE_URL + "/tv/" + mediaId + "/recommendations?api_key=" + TMDB_API_KEY + "&language=" + lang;
            case ALBUM:
                throw new IllegalArgumentException("Recomendações TMDB indisponíveis para mediaType: ALBUM");
            default:
                throw new IllegalArgumentException("MediaType inválido: " + mediaType);
        }
    }

    /**
     * Busca os gêneros do TMDB para filmes ou séries no idioma solicitado
     */
    private Map<Integer, String> fetchGenres(MediaType mediaType, String lang, RestTemplate restTemplate) {
        String endpoint;
        if (mediaType == MediaType.MOVIE) {
            endpoint = "/genre/movie/list";
        } else if (mediaType == MediaType.SERIES) {
            endpoint = "/genre/tv/list";
        } else {
            return Collections.emptyMap();
        }

        String url = String.format("%s%s?api_key=%s&language=%s", TMDB_BASE_URL, endpoint, TMDB_API_KEY, lang);

        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> genres = (List<Map<String, Object>>) response.getOrDefault("genres", Collections.emptyList());
            return genres.stream()
                    .collect(Collectors.toMap(g -> (Integer) g.get("id"), g -> (String) g.get("name")));
        } catch (Exception e) {
            System.out.println("Erro ao buscar gêneros TMDB: " + e.getMessage());
            return Collections.emptyMap();
        }
    }
}
