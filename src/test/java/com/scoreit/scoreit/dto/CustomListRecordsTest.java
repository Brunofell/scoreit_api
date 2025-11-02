package com.scoreit.scoreit.dto;


import com.scoreit.scoreit.dto.customList.CustomListContentData;
import com.scoreit.scoreit.dto.customList.CustomListRegisterData;
import com.scoreit.scoreit.dto.customList.CustomListUpdateData;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CustomListRecordsTest {

    @Test
    void shouldStoreValuesInCustomListContentData() {
        CustomListContentData data = new CustomListContentData(1L, "M123", "MOVIE", "Minha Lista");

        assertEquals(1L, data.memberId());
        assertEquals("M123", data.mediaId());
        assertEquals("MOVIE", data.mediaType());
        assertEquals("Minha Lista", data.listName());
    }

    @Test
    void shouldStoreValuesInCustomListRegisterData() {
        CustomListRegisterData registerData = new CustomListRegisterData(2L, "Lista Favorita", "Descrição teste");

        assertEquals(2L, registerData.memberId());
        assertEquals("Lista Favorita", registerData.listName());
        assertEquals("Descrição teste", registerData.description());
    }

    @Test
    void shouldStoreValuesInCustomListUpdateData() {
        CustomListUpdateData updateData = new CustomListUpdateData(3L, "Nova Lista", "Nova descrição");

        assertEquals(3L, updateData.id());
        assertEquals("Nova Lista", updateData.listName());
        assertEquals("Nova descrição", updateData.list_description());
    }
}
