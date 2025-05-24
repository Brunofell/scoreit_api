package com.scoreit.scoreit.controller;

import com.scoreit.scoreit.entity.Member;
import com.scoreit.scoreit.service.MemberFollowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/followers")
public class MemberFollowerController {

    @Autowired
    private MemberFollowerService memberFollowerService;

    // Seguir
    @PostMapping("/follow")
    public void follow(@RequestParam Long followerId, @RequestParam Long followedId) {
        memberFollowerService.follow(followerId, followedId);
    }

    // Deixar de seguir
    @PostMapping("/unfollow")
    public void unfollow(@RequestParam Long followerId, @RequestParam Long followedId) {
        memberFollowerService.unfollow(followerId, followedId);
    }

    // Ver seguidores
    @GetMapping("/{memberId}/followers")
    public List<Member> getFollowers(@PathVariable Long memberId) {
        return memberFollowerService.getFollowers(memberId);
    }

    // Ver quem está seguindo
    @GetMapping("/{memberId}/following")
    public List<Member> getFollowing(@PathVariable Long memberId) {
        return memberFollowerService.getFollowing(memberId);
    }

    // Verificar se está seguindo
    @GetMapping("/isFollowing")
    public boolean isFollowing(@RequestParam Long followerId, @RequestParam Long followedId) {
        return memberFollowerService.isFollowing(followerId, followedId);
    }

    // Contar seguidores
    @GetMapping("/{memberId}/countFollowers")
    public long countFollowers(@PathVariable Long memberId) {
        return memberFollowerService.countFollowers(memberId);
    }

    // Contar seguindo
    @GetMapping("/{memberId}/countFollowing")
    public long countFollowing(@PathVariable Long memberId) {
        return memberFollowerService.countFollowing(memberId);
    }
}
