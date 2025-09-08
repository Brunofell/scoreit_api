package com.scoreit.scoreit.service;

import com.scoreit.scoreit.entity.Review;
import com.scoreit.scoreit.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    private final String TMDB_API_KEY = "4b6d83522f71b2da2b7b52c4386131e00"; // pode vir de application.properties

    public List<Map<String, Object>> getRecommendations(Long memberId, String mediaType) {
        // pega os top 3 mais bem avaliados do usuário
        List<Review> topReviews = reviewRepository.findTopByMemberAndMediaType(memberId, mediaType, PageRequest.of(0, 3));

        List<Map<String, Object>> recommendations = new ArrayList<>();

        for (Review review : topReviews) {
            String url = buildTmdbUrl(review.getMediaId(), mediaType);

            try {
                // Chamada à API da TMDB
                RestTemplate restTemplate = new RestTemplate();
                Map<String, Object> response = restTemplate.getForObject(url, Map.class);

                if (response != null && response.containsKey("results")) {
                    List<Map<String, Object>> results = (List<Map<String, Object>>) response.get("results");
                    recommendations.addAll(results);
                }
            } catch (Exception e) {
                System.out.println("Erro ao chamar TMDB: " + e.getMessage());
            }
        }

        // opcional: remover duplicados pelo mediaId
        return recommendations.stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.toMap(r -> r.get("id"), r -> r, (r1, r2) -> r1),
                        m -> new ArrayList<>(m.values())
                ));
    }

    private String buildTmdbUrl(String mediaId, String mediaType) {
        String base = "https://api.themoviedb.org/3";
        if ("movie".equalsIgnoreCase(mediaType)) {
            return base + "/movie/" + mediaId + "/recommendations?api_key=" + TMDB_API_KEY + "&language=pt-BR";
        } else if ("series".equalsIgnoreCase(mediaType)) {
            return base + "/tv/" + mediaId + "/recommendations?api_key=" + TMDB_API_KEY + "&language=pt-BR";
        }
        throw new IllegalArgumentException("MediaType inválido: " + mediaType);
    }
}
