package com.scoreit.scoreit.service;

import com.scoreit.scoreit.dto.badge.BadgeResponse;
import com.scoreit.scoreit.entity.Member;
import com.scoreit.scoreit.repository.MemberBadgeRepository;
import com.scoreit.scoreit.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BadgeService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberBadgeRepository memberBadgeRepository;

    public List<BadgeResponse> getBadgesByMemberId(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found with id: " + memberId));

        return memberBadgeRepository.findByMember(member).stream()
                .map(mb -> new BadgeResponse(
                        mb.getBadge().getId(),
                        mb.getBadge().getName(),
                        mb.getBadge().getDescription()
                ))
                .toList();
    }
}