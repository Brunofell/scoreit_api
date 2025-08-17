package com.scoreit.scoreit.service;

import com.scoreit.scoreit.entity.Badge;
import com.scoreit.scoreit.entity.Member;
import com.scoreit.scoreit.entity.MemberBadge;
import com.scoreit.scoreit.repository.BadgeRepository;
import com.scoreit.scoreit.repository.MemberBadgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AchievementService {

    @Autowired
    private BadgeRepository badgeRepository;

    @Autowired
    private MemberBadgeRepository memberBadgeRepository;

    public void checkReviewAchievements(Member member, long totalReviews) {
        if (totalReviews == 1) {
            grantBadge(member, "FIRST_REVIEW");
        }
    }

    private void grantBadge(Member member, String code) {
        Badge badge = badgeRepository.findByCode(code);

        if (badge != null && !memberBadgeRepository.existsByMemberAndBadge(member, badge)) {
            MemberBadge memberBadge = new MemberBadge(member, badge);
            memberBadgeRepository.save(memberBadge);
        }
    }
}