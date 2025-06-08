package com.scoreit.scoreit.controller;

import com.scoreit.scoreit.dto.feed.FeedResponse;
import com.scoreit.scoreit.service.FeedService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feed")
public class FeedController {
    @Autowired
    private FeedService feedService;

    @GetMapping("/{id}")
    public ResponseEntity<List<FeedResponse>> getFeedById(@PathVariable Long id){
        return ResponseEntity.ok(feedService.montaFeed(id));
    }
}
