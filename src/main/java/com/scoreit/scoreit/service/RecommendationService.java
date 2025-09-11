package com.scoreit.scoreit.service;

import com.scoreit.scoreit.dto.review.MediaType;
import com.scoreit.scoreit.entity.Review;
import com.scoreit.scoreit.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RecommendationService {

    @Autowired
    private ReviewRepository reviewRepository;

    // ideal: ler de application.properties (ex.: @Value("${tmdb.api.key}"))
    private final String TMDB_API_KEY = "4b6d83522f71b2da2b7b52c4386131e00";

    /**
     * Versão compatível com quem chama usando String.
     * Aceita "movie" | "series" | "album" (qualquer case).
     */
    public List<Map<String, Object>> getRecommendations(Long memberId, String mediaType) {
        MediaType mt = MediaType.fromJson(mediaType); // normaliza para enum (uppercase)
        if (mt == null) {
            throw new IllegalArgumentException("MediaType inválido: " + mediaType);
        }
        return getRecommendations(memberId, mt);
    }

    /**
     * Versão tipo-safe usando o enum MediaType.
     */
    public List<Map<String, Object>> getRecommendations(Long memberId, MediaType mediaType) {
        // pega os top 3 mais bem avaliados do usuário para aquele tipo
        List<Review> topReviews = reviewRepository.findTopByMemberAndMediaType(
                memberId, mediaType, PageRequest.of(0, 3)
        );

        List<Map<String, Object>> recommendations = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();

        for (Review review : topReviews) {
            String url = buildTmdbUrl(review.getMediaId(), mediaType);

            try {
                @SuppressWarnings("unchecked")
                Map<String, Object> response = restTemplate.getForObject(url, Map.class);

                if (response != null && response.containsKey("results")) {
                    @SuppressWarnings("unchecked")
                    List<Map<String, Object>> results = (List<Map<String, Object>>) response.get("results");
                    if (results != null) {
                        recommendations.addAll(results);
                    }
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

    private String buildTmdbUrl(String mediaId, MediaType mediaType) {
        String base = "https://api.themoviedb.org/3";
        switch (mediaType) {
            case MOVIE:
                return base + "/movie/" + mediaId + "/recommendations?api_key=" + TMDB_API_KEY + "&language=pt-BR";
            case SERIES:
                return base + "/tv/" + mediaId + "/recommendations?api_key=" + TMDB_API_KEY + "&language=pt-BR";
            case ALBUM:
                // TMDB não tem "album" — lance erro explícito pra não parecer falha silenciosa
                throw new IllegalArgumentException("Recomendações TMDB indisponíveis para mediaType: ALBUM");
            default:
                throw new IllegalArgumentException("MediaType inválido: " + mediaType);
        }
    }
}
