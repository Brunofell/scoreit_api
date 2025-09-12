package com.scoreit.scoreit.controller;

import com.scoreit.scoreit.dto.badge.BadgeResponse;
import com.scoreit.scoreit.service.BadgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/member")
public class BadgeController {

    @Autowired
    private BadgeService badgeService;

    @GetMapping("/{memberId}/badges")
    public ResponseEntity<List<BadgeResponse>> getMemberBadges(@PathVariable Long memberId) {
        return ResponseEntity.ok(badgeService.getBadgesByMemberId(memberId));
    }
}
