package com.scoreit.scoreit.service;

import com.scoreit.scoreit.entity.FavoriteList;
import com.scoreit.scoreit.entity.Member;
import com.scoreit.scoreit.repository.FavoriteListRepository;
import com.scoreit.scoreit.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FavoriteListServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private FavoriteListRepository favoriteListRepository;

    @InjectMocks
    private FavoriteListService favoriteListService;

    private Member member;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        member = new Member();
        member.setId(1L);
        member.setName("Bruno");
        member.setEmail("bruno@example.com");
    }

    @Test
    void shouldCreateFavoriteListSuccessfully() {
        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));

        FavoriteList savedList = new FavoriteList();
        savedList.setId(1L);
        savedList.setMember(member);
        savedList.setListName("My Favorites");
        savedList.setList_description("Description");
        when(favoriteListRepository.save(any(FavoriteList.class))).thenReturn(savedList);

        FavoriteList result = favoriteListService.createFavoriteList(1L, "My Favorites", "Description");

        assertNotNull(result);
        assertEquals("My Favorites", result.getListName());
        assertEquals(member, result.getMember());
        verify(favoriteListRepository).save(any(FavoriteList.class));
    }

    @Test
    void shouldThrowIfMemberNotFound() {
        when(memberRepository.findById(2L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> favoriteListService.createFavoriteList(2L, "List", "Desc"));

        assertEquals("Member Not Found.", ex.getMessage());
    }

    @Test
    void shouldReturnListByMemberId() {
        FavoriteList favoriteList = new FavoriteList();
        when(favoriteListRepository.findByMemberId(1L)).thenReturn(Optional.of(favoriteList));

        Optional<FavoriteList> result = favoriteListService.getListByMemberId(1L);

        assertTrue(result.isPresent());
        assertEquals(favoriteList, result.get());
    }

    @Test
    void shouldReturnEmptyOptionalIfNoList() {
        when(favoriteListRepository.findByMemberId(2L)).thenReturn(Optional.empty());

        Optional<FavoriteList> result = favoriteListService.getListByMemberId(2L);

        assertTrue(result.isEmpty());
    }
}
