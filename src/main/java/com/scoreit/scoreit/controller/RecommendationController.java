package com.scoreit.scoreit.controller;

import com.scoreit.scoreit.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/recommendations")
public class RecommendationController {

    @Autowired
    private RecommendationService recommendationService;

    /**
     * Busca recomendações de filmes ou séries para um membro
     * @param memberId ID do membro
     * @param mediaType Tipo de mídia: MOVIE | SERIES
     * @param lang Idioma (opcional), ex: pt-BR ou en-US
     */
    @GetMapping("/{memberId}/{mediaType}")
    public ResponseEntity<List<Map<String, Object>>> getRecommendations(
            @PathVariable Long memberId,
            @PathVariable String mediaType,
            @RequestParam(name = "lang", required = false, defaultValue = "pt-BR") String lang
    ) {
        List<Map<String, Object>> recs = recommendationService.getRecommendations(memberId, mediaType, lang);
        return ResponseEntity.ok(recs);
    }
}
