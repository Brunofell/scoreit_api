package com.scoreit.scoreit.controller;

import com.scoreit.scoreit.dto.feed.FeedResponse;
import com.scoreit.scoreit.service.FeedService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/feed")
public class FeedController {

    private final FeedService feedService;

    public FeedController(FeedService feedService) {
        this.feedService = feedService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<FeedResponse>> getFeedById(
            @PathVariable Long id,
            @RequestParam(value = "language", required = false) String language,
            @RequestHeader(value = "Accept-Language", required = false) String acceptLanguage,
            @AuthenticationPrincipal(expression = "id") Long authenticatedMemberId
    ) {
        // Prioridade: query param `language` > Accept-Language header > fallback "pt-BR"
        String resolved = resolveLanguage(language, acceptLanguage);
        List<FeedResponse> feed = feedService.montaFeed(id, resolved);
        return ResponseEntity.ok(feed);
    }

    private String resolveLanguage(String languageParam, String acceptLanguageHeader) {
        if (languageParam != null && !languageParam.isBlank()) {
            return languageParam;
        }
        if (acceptLanguageHeader != null && !acceptLanguageHeader.isBlank()) {
            // pega a primeira parte do header (ex: "en-US,en;q=0.9" -> "en-US")
            String first = acceptLanguageHeader.split(",")[0].trim();
            return first;
        }
        return "pt-BR";
    }
}
