package com.scoreit.scoreit.dto;


import com.scoreit.scoreit.api.tmdb.movie.dto.person.KnownFor;
import com.scoreit.scoreit.api.tmdb.movie.dto.person.Person;
import com.scoreit.scoreit.api.tmdb.movie.dto.person.PersonSearchResponse;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PersonDtoTest {

    @Test
    void shouldCreateKnownForCorrectly() {
        KnownFor knownForMovie = new KnownFor(
                101,
                "Movie Title",
                null,
                "movie",
                "/poster.jpg",
                "Overview do filme"
        );

        KnownFor knownForSeries = new KnownFor(
                202,
                null,
                "Series Name",
                "tv",
                "/poster2.jpg",
                "Overview da série"
        );

        assertEquals(101, knownForMovie.id());
        assertEquals("Movie Title", knownForMovie.title());
        assertNull(knownForMovie.name());
        assertEquals("movie", knownForMovie.media_type());
        assertEquals("/poster.jpg", knownForMovie.poster_path());
        assertEquals("Overview do filme", knownForMovie.overview());

        assertEquals(202, knownForSeries.id());
        assertEquals("Series Name", knownForSeries.name());
        assertNull(knownForSeries.title());
        assertEquals("tv", knownForSeries.media_type());
        assertEquals("/poster2.jpg", knownForSeries.poster_path());
        assertEquals("Overview da série", knownForSeries.overview());
    }

    @Test
    void shouldCreatePersonCorrectly() {
        KnownFor knownFor = new KnownFor(101, "Movie Title", null, "movie", "/poster.jpg", "Overview");
        Person person = new Person(1, "John Doe", "/profile.jpg", List.of(knownFor));

        assertEquals(1, person.id());
        assertEquals("John Doe", person.name());
        assertEquals("/profile.jpg", person.profile_path());
        assertNotNull(person.known_for());
        assertEquals(1, person.known_for().size());
        assertEquals(knownFor, person.known_for().get(0));
    }

    @Test
    void shouldCreatePersonSearchResponseCorrectly() {
        KnownFor knownFor = new KnownFor(101, "Movie Title", null, "movie", "/poster.jpg", "Overview");
        Person person = new Person(1, "John Doe", "/profile.jpg", List.of(knownFor));
        PersonSearchResponse response = new PersonSearchResponse(1, List.of(person), 2, 10);

        assertEquals(1, response.page());
        assertNotNull(response.results());
        assertEquals(1, response.results().size());
        assertEquals(person, response.results().get(0));
        assertEquals(2, response.total_pages());
        assertEquals(10, response.total_results());
    }
}