package com.scoreit.scoreit.controller;

import com.scoreit.scoreit.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/recommendations")
public class RecommendationController {

    @Autowired
    private RecommendationService recommendationService;

    @GetMapping("/{memberId}/{mediaType}")
    public ResponseEntity<List<Map<String, Object>>> getRecommendations(
            @PathVariable Long memberId,
            @PathVariable String mediaType) {

        List<Map<String, Object>> recs = recommendationService.getRecommendations(memberId, mediaType);
        return ResponseEntity.ok(recs);
    }
}
