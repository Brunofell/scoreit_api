package com.scoreit.scoreit.dto;


import com.scoreit.scoreit.dto.graph.MediaPopularityDTO;
import com.scoreit.scoreit.dto.graph.ReviewsByDateDTO;
import com.scoreit.scoreit.dto.graph.UsersGrowthDTO;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class GraphDTOsTest {

    @Test
    void shouldStoreValuesInMediaPopularityDTO() {
        MediaPopularityDTO dto = new MediaPopularityDTO("M123", "MOVIE", 42L);

        assertEquals("M123", dto.mediaId());
        assertEquals("MOVIE", dto.mediaType());
        assertEquals(42L, dto.total());
    }

    @Test
    void shouldStoreValuesInReviewsByDateDTO() {
        LocalDate date = LocalDate.of(2025, 11, 1);
        ReviewsByDateDTO dto = new ReviewsByDateDTO(date, 15L);

        assertEquals(date, dto.date());
        assertEquals(15L, dto.total());
    }

    @Test
    void shouldStoreValuesInUsersGrowthDTO() {
        UsersGrowthDTO dto = new UsersGrowthDTO("Nov/2025", 100L);

        assertEquals("Nov/2025", dto.month());
        assertEquals(100L, dto.total());
    }
}
