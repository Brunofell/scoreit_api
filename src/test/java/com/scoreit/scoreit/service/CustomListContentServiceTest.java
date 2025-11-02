package com.scoreit.scoreit.service;

import com.scoreit.scoreit.entity.CustomList;
import com.scoreit.scoreit.entity.CustomListContent;
import com.scoreit.scoreit.entity.Member;
import com.scoreit.scoreit.repository.CustomListContentRepository;
import com.scoreit.scoreit.repository.CustomListRepository;
import com.scoreit.scoreit.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomListContentServiceTest {

    @Mock
    private CustomListContentRepository customListContentRepository;

    @Mock
    private CustomListRepository customListRepository;

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private CustomListContentService customListContentService;

    private Member member;
    private CustomList customList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        member = new Member();
        member.setId(1L);

        customList = new CustomList();
        customList.setId(10L);
        customList.setListName("Favoritos");
        customList.setMember(member);
    }

    @Test
    void shouldAddContentSuccessfully() {
        // Arrange
        when(customListRepository.findByMemberIdAndListName(1L, "Favoritos")).thenReturn(customList);
        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));
        when(customListContentRepository.findByCustomListAndMediaIdAndMediaType(customList, "M1", "MOVIE"))
                .thenReturn(Optional.empty());

        CustomListContent savedContent = new CustomListContent();
        savedContent.setMediaId("M1");
        savedContent.setMediaType("MOVIE");
        when(customListContentRepository.save(any(CustomListContent.class))).thenReturn(savedContent);

        // Act
        customListContentService.addContentInCustom(1L, "M1", "MOVIE", "Favoritos");

        // Assert
        verify(customListContentRepository, times(1)).save(any(CustomListContent.class));
    }

    @Test
    void shouldThrowWhenAddingExistingContent() {
        // Arrange
        when(customListRepository.findByMemberIdAndListName(1L, "Favoritos")).thenReturn(customList);
        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));

        CustomListContent existing = new CustomListContent();
        when(customListContentRepository.findByCustomListAndMediaIdAndMediaType(customList, "M1", "MOVIE"))
                .thenReturn(Optional.of(existing));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> customListContentService.addContentInCustom(1L, "M1", "MOVIE", "Favoritos"));
        assertEquals("This content is already in your list.", exception.getMessage());
    }

    @Test
    void shouldGetCustomListContent() {
        // Arrange
        when(customListRepository.findByMemberIdAndListName(1L, "Favoritos")).thenReturn(customList);

        CustomListContent content = new CustomListContent();
        content.setMediaId("M1");
        content.setMediaType("MOVIE");
        when(customListContentRepository.findByCustomList(customList)).thenReturn(List.of(content));

        // Act
        List<CustomListContent> result = customListContentService.getCustomListContent(1L, "Favoritos");

        // Assert
        assertEquals(1, result.size());
        assertEquals("M1", result.get(0).getMediaId());
    }

    @Test
    void shouldThrowWhenListNotFoundOnGet() {
        // Arrange
        when(customListRepository.findByMemberIdAndListName(1L, "Favoritos")).thenReturn(null);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> customListContentService.getCustomListContent(1L, "Favoritos"));
        assertEquals("Lista não encontrada.", exception.getMessage());
    }

    @Test
    void shouldRemoveContentSuccessfully() {
        // Arrange
        when(customListRepository.findByMemberIdAndListName(1L, "Favoritos")).thenReturn(customList);

        CustomListContent content = new CustomListContent();
        when(customListContentRepository.findByCustomListAndMediaIdAndMediaType(customList, "M1", "MOVIE"))
                .thenReturn(Optional.of(content));

        // Act
        customListContentService.removeContent(1L, "M1", "MOVIE", "Favoritos");

        // Assert
        verify(customListContentRepository, times(1)).delete(content);
    }

    @Test
    void shouldThrowWhenContentNotFoundOnRemove() {
        // Arrange
        when(customListRepository.findByMemberIdAndListName(1L, "Favoritos")).thenReturn(customList);
        when(customListContentRepository.findByCustomListAndMediaIdAndMediaType(customList, "M1", "MOVIE"))
                .thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> customListContentService.removeContent(1L, "M1", "MOVIE", "Favoritos"));
        assertEquals("Conteúdo não encontrado na lista.", exception.getMessage());
    }

    @Test
    void shouldThrowWhenListNotFoundOnRemove() {
        // Arrange
        when(customListRepository.findByMemberIdAndListName(1L, "Favoritos")).thenReturn(null);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> customListContentService.removeContent(1L, "M1", "MOVIE", "Favoritos"));
        assertEquals("Lista não encontrada.", exception.getMessage());
    }
}
