package com.scoreit.scoreit.service;

import com.scoreit.scoreit.dto.badge.AchievementRule;
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

    /**
     * Checa se o usuário atingiu alguma conquista de reviews de filmes
     */
    public void checkReviewAchievements(Member member, long totalReviews, String mediaType) {
        for (AchievementRule rule : AchievementRule.values()) {
            if (rule.getType().equals(mediaType) && rule.getThreshold() == totalReviews) {
                grantBadge(member, rule.getCode());
            }
        }
    }

    /**
     * Concede a badge ao usuário, se ele ainda não tiver
     */
    private void grantBadge(Member member, String code) {
        Badge badge = badgeRepository.findByCode(code);
        if (badge != null && !memberBadgeRepository.existsByMemberAndBadge(member, badge)) {
            memberBadgeRepository.save(new MemberBadge(member, badge));
            System.out.println("🏆 Conquista desbloqueada: " + badge.getName());
        }
    }
}
