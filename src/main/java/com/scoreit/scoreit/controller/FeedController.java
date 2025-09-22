package com.scoreit.scoreit.controller;

import com.scoreit.scoreit.dto.feed.FeedResponse;
import com.scoreit.scoreit.service.FeedService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feed")
public class FeedController {

    private final FeedService feedService;

    public FeedController(FeedService feedService) {
        this.feedService = feedService;
    }

    @GetMapping("/{id}")
    // Garante que só usuários autenticados conseguem acessar
    public ResponseEntity<List<FeedResponse>> getFeedById(
            @PathVariable Long id,
            @RequestParam(defaultValue = "pt-BR") String language,
            @AuthenticationPrincipal(expression = "id") Long authenticatedMemberId
    ) {
        // Opcional: só permite acessar seu próprio feed ou dos que segue
        if (!authenticatedMemberId.equals(id)) {
            return ResponseEntity.status(403).build();
        }

        List<FeedResponse> feed = feedService.montaFeed(id, language);
        return ResponseEntity.ok(feed);
    }
}
