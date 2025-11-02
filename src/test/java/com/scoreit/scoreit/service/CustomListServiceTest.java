package com.scoreit.scoreit.service;

import com.scoreit.scoreit.dto.customList.CustomListUpdateData;
import com.scoreit.scoreit.entity.CustomList;
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

class CustomListServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private CustomListRepository customListRepository;

    @Mock
    private CustomListContentRepository customListContentRepository;

    @InjectMocks
    private CustomListService customListService;

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
    void shouldCreateCustomListSuccessfully() {
        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));
        when(customListRepository.save(any(CustomList.class))).thenReturn(customList);

        CustomList result = customListService.createCustomList(1L, "Favoritos", "Minha lista");

        assertNotNull(result);
        assertEquals("Favoritos", result.getListName());
        verify(customListRepository).save(any(CustomList.class));
    }

    @Test
    void shouldThrowWhenMemberNotFoundOnCreate() {
        when(memberRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> customListService.createCustomList(1L, "Favoritos", "Minha lista"));

        assertEquals("Member Not Found.", exception.getMessage());
    }

    @Test
    void shouldGetListByMemberId() {
        when(customListRepository.findByMemberId(1L)).thenReturn(List.of(customList));

        List<CustomList> result = customListService.getListByMemberId(1L);

        assertEquals(1, result.size());
        assertEquals("Favoritos", result.get(0).getListName());
    }

    @Test
    void shouldDeleteCustomListSuccessfully() {
        when(customListRepository.findById(10L)).thenReturn(Optional.of(customList));
        when(customListContentRepository.findByCustomList(customList)).thenReturn(List.of());

        customListService.deleteCustomListById(10L);

        verify(customListContentRepository).deleteAll(anyList());
        verify(customListRepository).delete(customList);
    }

    @Test
    void shouldThrowWhenListNotFoundOnDelete() {
        when(customListRepository.findById(10L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> customListService.deleteCustomListById(10L));

        assertEquals("Lista não encontrada.", exception.getMessage());
    }

    @Test
    void shouldUpdateListDataSuccessfully() {
        CustomListUpdateData data = new CustomListUpdateData(10L, "Nova lista", "Nova descrição");

        when(customListRepository.getReferenceById(10L)).thenReturn(customList);
        when(customListRepository.save(customList)).thenReturn(customList);

        CustomList result = customListService.updateListData(data);

        assertNotNull(result);
        verify(customListRepository).save(customList);
    }
}
