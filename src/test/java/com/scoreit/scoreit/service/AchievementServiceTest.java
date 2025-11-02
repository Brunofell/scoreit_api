package com.scoreit.scoreit.service;

import com.scoreit.scoreit.entity.Badge;
import com.scoreit.scoreit.entity.Member;
import com.scoreit.scoreit.entity.MemberBadge;
import com.scoreit.scoreit.repository.BadgeRepository;
import com.scoreit.scoreit.repository.MemberBadgeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.mockito.Mockito.*;

class AchievementServiceTest {

    @Mock
    private BadgeRepository badgeRepository;

    @Mock
    private MemberBadgeRepository memberBadgeRepository;

    @InjectMocks
    private AchievementService achievementService;

    private Member member;
    private Badge badge;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        member = new Member();
        member.setId(1L);
        member.setName("Bruno");

        badge = new Badge();
        badge.setId(1L);
        badge.setCode("MOVIE_10");
        badge.setName("Cinéfilo Nível 1");
    }

    @Test
    void shouldGrantBadgeWhenRuleIsSatisfied() {
        // Arrange
        when(badgeRepository.findByCode("MOVIE_10")).thenReturn(badge);
        when(memberBadgeRepository.existsByMemberAndBadge(member, badge)).thenReturn(false);

        // Act
        achievementService.checkReviewAchievements(member, 10, "MOVIE");

        // Assert
        verify(memberBadgeRepository, atLeastOnce()).save(any(MemberBadge.class));
    }

    @Test
    void shouldNotGrantBadgeIfAlreadyHasIt() {
        // Arrange
        when(badgeRepository.findByCode("MOVIE_10")).thenReturn(badge);
        when(memberBadgeRepository.existsByMemberAndBadge(member, badge)).thenReturn(true);

        // Act
        achievementService.checkReviewAchievements(member, 20, "MOVIE");

        // Assert
        verify(memberBadgeRepository, never()).save(any(MemberBadge.class));
    }

    @Test
    void shouldNotGrantBadgeIfBadgeNotFound() {
        // Arrange
        when(badgeRepository.findByCode(anyString())).thenReturn(null);

        // Act
        achievementService.checkReviewAchievements(member, 15, "MOVIE");

        // Assert
        verify(memberBadgeRepository, never()).save(any(MemberBadge.class));
    }

    @Test
    void shouldReconcileAchievementsForAllMediaTypes() {
        // Arrange
        when(badgeRepository.findByCode(anyString())).thenReturn(badge);
        when(memberBadgeRepository.existsByMemberAndBadge(any(), any())).thenReturn(false);

        // Act
        achievementService.reconcileMemberAchievements(member, 15, 20, 30);

        // Assert
        // Como esse método chama checkReviewAchievements três vezes (MOVIE, SERIES, ALBUM)
        // verificamos que save() foi chamado várias vezes.
        verify(memberBadgeRepository, atLeast(3)).save(any(MemberBadge.class));
    }
}
