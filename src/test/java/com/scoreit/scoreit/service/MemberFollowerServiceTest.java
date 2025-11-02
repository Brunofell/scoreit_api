package com.scoreit.scoreit.service;

import com.scoreit.scoreit.entity.Member;
import com.scoreit.scoreit.entity.MemberFollower;
import com.scoreit.scoreit.entity.MemberFollowerId;
import com.scoreit.scoreit.repository.MemberFollowerRepository;
import com.scoreit.scoreit.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MemberFollowerServiceTest {

    @Mock
    private MemberFollowerRepository memberFollowerRepository;

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberFollowerService memberFollowerService;

    private Member follower;
    private Member followed;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        follower = new Member();
        follower.setId(1L);
        follower.setName("Follower");
        follower.setFollowing_num(0);

        followed = new Member();
        followed.setId(2L);
        followed.setName("Followed");
        followed.setFollowers(0);
    }

    @Test
    void testFollowSuccess() {
        when(memberRepository.findById(1L)).thenReturn(Optional.of(follower));
        when(memberRepository.findById(2L)).thenReturn(Optional.of(followed));
        when(memberFollowerRepository.existsById(new MemberFollowerId(1L, 2L))).thenReturn(false);

        memberFollowerService.follow(1L, 2L);

        assertEquals(1, follower.getFollowing_num());
        assertEquals(1, followed.getFollowers());
        verify(memberFollowerRepository).save(any(MemberFollower.class));
        verify(memberRepository).save(follower);
        verify(memberRepository).save(followed);
    }

    @Test
    void testFollowSelfThrows() {
        RuntimeException ex = assertThrows(RuntimeException.class, () -> memberFollowerService.follow(1L, 1L));
        assertEquals("Você não pode seguir a si mesmo.", ex.getMessage());
    }

    @Test
    void testFollowAlreadyFollowingThrows() {
        when(memberRepository.findById(1L)).thenReturn(Optional.of(follower));
        when(memberRepository.findById(2L)).thenReturn(Optional.of(followed));
        when(memberFollowerRepository.existsById(new MemberFollowerId(1L, 2L))).thenReturn(true);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> memberFollowerService.follow(1L, 2L));
        assertEquals("Você já segue este usuário.", ex.getMessage());
    }

    @Test
    void testUnfollowSuccess() {
        follower.setFollowing_num(1);
        followed.setFollowers(1);
        when(memberRepository.findById(1L)).thenReturn(Optional.of(follower));
        when(memberRepository.findById(2L)).thenReturn(Optional.of(followed));
        when(memberFollowerRepository.existsById(new MemberFollowerId(1L, 2L))).thenReturn(true);

        memberFollowerService.unfollow(1L, 2L);

        assertEquals(0, follower.getFollowing_num());
        assertEquals(0, followed.getFollowers());
        verify(memberFollowerRepository).deleteById(new MemberFollowerId(1L, 2L));
        verify(memberRepository).save(follower);
        verify(memberRepository).save(followed);
    }

    @Test
    void testGetFollowers() {
        MemberFollower mf = new MemberFollower(follower, followed);
        when(memberRepository.findById(2L)).thenReturn(Optional.of(followed));
        when(memberFollowerRepository.findByFollowed(followed)).thenReturn(List.of(mf));

        List<Member> followers = memberFollowerService.getFollowers(2L);

        assertEquals(1, followers.size());
        assertEquals(follower.getId(), followers.get(0).getId());
    }

    @Test
    void testGetFollowing() {
        MemberFollower mf = new MemberFollower(follower, followed);
        when(memberRepository.findById(1L)).thenReturn(Optional.of(follower));
        when(memberFollowerRepository.findByFollower(follower)).thenReturn(List.of(mf));

        List<Member> following = memberFollowerService.getFollowing(1L);

        assertEquals(1, following.size());
        assertEquals(followed.getId(), following.get(0).getId());
    }

    @Test
    void testIsFollowing() {
        MemberFollowerId id = new MemberFollowerId(1L, 2L);
        when(memberFollowerRepository.existsById(id)).thenReturn(true);

        assertTrue(memberFollowerService.isFollowing(1L, 2L));
    }

    @Test
    void testCountFollowersAndFollowing() {
        when(memberRepository.findById(1L)).thenReturn(Optional.of(follower));
        when(memberRepository.findById(2L)).thenReturn(Optional.of(followed));
        when(memberFollowerRepository.countByFollowed(followed)).thenReturn(5L);
        when(memberFollowerRepository.countByFollower(follower)).thenReturn(10L);

        assertEquals(5L, memberFollowerService.countFollowers(2L));
        assertEquals(10L, memberFollowerService.countFollowing(1L));
    }
}
