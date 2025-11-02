package com.scoreit.scoreit.dto;


import com.scoreit.scoreit.dto.favoriteList.TopMediaProjection;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TopMediaProjectionTest {

    @Test
    void shouldStoreValuesCorrectly() {
        TopMediaProjection top = new TopMediaProjection("movie123", 42L);

        assertEquals("movie123", top.mediaId());
        assertEquals(42L, top.total());
    }

    @Test
    void shouldHaveCorrectToString() {
        TopMediaProjection top = new TopMediaProjection("movie123", 42L);

        String expected = "TopMediaProjection[mediaId=movie123, total=42]";
        assertTrue(top.toString().contains("movie123"));
        assertTrue(top.toString().contains("42"));
    }

    @Test
    void shouldBeEqualByValues() {
        TopMediaProjection top1 = new TopMediaProjection("movie123", 42L);
        TopMediaProjection top2 = new TopMediaProjection("movie123", 42L);
        TopMediaProjection top3 = new TopMediaProjection("movie456", 10L);

        assertEquals(top1, top2);
        assertNotEquals(top1, top3);
    }
}
