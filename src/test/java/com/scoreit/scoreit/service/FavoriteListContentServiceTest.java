package com.scoreit.scoreit.service;

import com.scoreit.scoreit.dto.favoriteList.TopMediaProjection;
import com.scoreit.scoreit.entity.FavoriteList;
import com.scoreit.scoreit.entity.FavoriteListContent;
import com.scoreit.scoreit.entity.Member;
import com.scoreit.scoreit.repository.FavoriteListContentRepository;
import com.scoreit.scoreit.repository.FavoriteListRepository;
import com.scoreit.scoreit.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FavoriteListContentServiceTest {

    @Mock
    private FavoriteListContentRepository favoriteListContentRepository;

    @Mock
    private FavoriteListRepository favoriteListRepository;

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private FavoriteListContentService favoriteListContentService;

    private Member member;
    private FavoriteList favoriteList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        member = new Member();
        member.setId(1L);
        member.setName("Bruno");
        member.setEmail("bruno@example.com");

        favoriteList = new FavoriteList();
        favoriteList.setId(1L);
        favoriteList.setMember(member);
        favoriteList.setListName("Favorites");
    }

    @Test
    void shouldAddContentSuccessfully() {
        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));
        when(favoriteListRepository.findByMemberIdAndListName(1L, "Favorites")).thenReturn(favoriteList);
        when(favoriteListContentRepository.findByFavoriteListAndMediaIdAndMediaType(favoriteList, "m1", "movie"))
                .thenReturn(Optional.empty());

        favoriteListContentService.addContentInFavorites(1L, "m1", "movie");

        verify(favoriteListContentRepository).save(any(FavoriteListContent.class));
    }

    @Test
    void shouldThrowIfContentAlreadyExists() {
        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));
        when(favoriteListRepository.findByMemberIdAndListName(1L, "Favorites")).thenReturn(favoriteList);
        when(favoriteListContentRepository.findByFavoriteListAndMediaIdAndMediaType(favoriteList, "m1", "movie"))
                .thenReturn(Optional.of(new FavoriteListContent()));

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> favoriteListContentService.addContentInFavorites(1L, "m1", "movie"));
        assertEquals("This content is already in your list.", ex.getMessage());
    }

    @Test
    void shouldCreateFavoriteListIfNotExists() {
        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));
        when(favoriteListRepository.findByMemberIdAndListName(1L, "Favorites")).thenReturn(null);
        when(favoriteListContentRepository.findByFavoriteListAndMediaIdAndMediaType(any(), eq("m1"), eq("movie")))
                .thenReturn(Optional.empty());

        favoriteListContentService.addContentInFavorites(1L, "m1", "movie");

        verify(favoriteListRepository).save(any(FavoriteList.class));
        verify(favoriteListContentRepository).save(any(FavoriteListContent.class));
    }

    @Test
    void shouldReturnFavoriteListContentOrEmpty() {
        when(favoriteListRepository.findByMemberIdAndListName(1L, "Favorites")).thenReturn(favoriteList);
        when(favoriteListContentRepository.findByFavoriteList(favoriteList))
                .thenReturn(List.of(new FavoriteListContent()));

        List<FavoriteListContent> contents = favoriteListContentService.getFavoriteListContent(1L);
        assertEquals(1, contents.size());

        when(favoriteListRepository.findByMemberIdAndListName(2L, "Favorites")).thenReturn(null);
        List<FavoriteListContent> emptyList = favoriteListContentService.getFavoriteListContent(2L);
        assertTrue(emptyList.isEmpty());
    }

    @Test
    void shouldRemoveContentSuccessfully() {
        when(favoriteListRepository.findByMemberIdAndListName(1L, "Favorites")).thenReturn(favoriteList);
        FavoriteListContent content = new FavoriteListContent();
        when(favoriteListContentRepository.findByFavoriteListAndMediaIdAndMediaType(favoriteList, "m1", "movie"))
                .thenReturn(Optional.of(content));

        favoriteListContentService.removeContent(1L, "m1", "movie");

        verify(favoriteListContentRepository).delete(content);
    }

    @Test
    void shouldThrowIfRemovingContentNotFound() {
        when(favoriteListRepository.findByMemberIdAndListName(1L, "Favorites")).thenReturn(favoriteList);
        when(favoriteListContentRepository.findByFavoriteListAndMediaIdAndMediaType(favoriteList, "m1", "movie"))
                .thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> favoriteListContentService.removeContent(1L, "m1", "movie"));
        assertEquals("Conteúdo não encontrado na lista de favoritos.", ex.getMessage());
    }

    @Test
    void shouldCheckIfContentIsFavorited() {
        when(favoriteListRepository.findByMemberIdAndListName(1L, "Favorites")).thenReturn(favoriteList);
        when(favoriteListContentRepository.findByFavoriteListAndMediaId(favoriteList, "m1"))
                .thenReturn(Optional.of(new FavoriteListContent()));

        assertTrue(favoriteListContentService.isContentFavorited(1L, "m1"));

        when(favoriteListContentRepository.findByFavoriteListAndMediaId(favoriteList, "m2"))
                .thenReturn(Optional.empty());

        assertFalse(favoriteListContentService.isContentFavorited(1L, "m2"));

        when(favoriteListRepository.findByMemberIdAndListName(2L, "Favorites")).thenReturn(null);
        assertFalse(favoriteListContentService.isContentFavorited(2L, "m1"));
    }

    @Test
    void shouldReturnTopFavoritedAlbums() {
        List<TopMediaProjection> topAlbums = List.of();
        when(favoriteListContentRepository.findFavoriteMedia("album", 5)).thenReturn(topAlbums);

        List<TopMediaProjection> result = favoriteListContentService.getTopFavoritedAlbums(5);
        assertEquals(topAlbums, result);
    }
}
