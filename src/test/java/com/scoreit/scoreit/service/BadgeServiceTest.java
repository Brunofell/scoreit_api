package com.scoreit.scoreit.service;

import com.scoreit.scoreit.dto.badge.BadgeResponse;
import com.scoreit.scoreit.entity.Badge;
import com.scoreit.scoreit.entity.Member;
import com.scoreit.scoreit.entity.MemberBadge;
import com.scoreit.scoreit.repository.MemberBadgeRepository;
import com.scoreit.scoreit.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BadgeServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private MemberBadgeRepository memberBadgeRepository;

    @InjectMocks
    private BadgeService badgeService;

    private Member member;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        member = new Member();
        member.setId(1L);
    }

    @Test
    void shouldReturnBadgesForMember() {
        // Arrange
        Badge badge1 = new Badge();
        badge1.setId(100L);
        badge1.setName("Badge 1");
        badge1.setDescription("Desc 1");
        badge1.setCode("B1");

        Badge badge2 = new Badge();
        badge2.setId(101L);
        badge2.setName("Badge 2");
        badge2.setDescription("Desc 2");
        badge2.setCode("B2");

        MemberBadge mb1 = new MemberBadge(member, badge1);
        MemberBadge mb2 = new MemberBadge(member, badge2);

        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));
        when(memberBadgeRepository.findByMember(member)).thenReturn(List.of(mb1, mb2));

        // Act
        List<BadgeResponse> badges = badgeService.getBadgesByMemberId(1L);

        // Assert
        assertEquals(2, badges.size());
        assertEquals("Badge 1", badges.get(0).name());
        assertEquals("B2", badges.get(1).code());
        verify(memberRepository, times(1)).findById(1L);
        verify(memberBadgeRepository, times(1)).findByMember(member);
    }

    @Test
    void shouldThrowExceptionIfMemberNotFound() {
        // Arrange
        when(memberRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> badgeService.getBadgesByMemberId(99L)
        );

        assertEquals("Member not found with id: 99", exception.getMessage());
        verify(memberRepository, times(1)).findById(99L);
        verifyNoInteractions(memberBadgeRepository);
    }
}
