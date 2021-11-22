package com.kinoticket.backend.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MovieTests {
    @Test
    void movieTest() {
        Movie movie = new Movie();

        String title = "Test Movie";
        int year = 2000;
        int fsk = 12;
        String shortDescription = "This is a Description";
        String description = "This is a full Description";
        String trailer = "someTrailerUrl";

        movie.setTitle(title);
        movie.setYear(year);
        movie.setFsk(fsk);
        movie.setShortDescription(shortDescription);
        movie.setDescription(description);
        movie.setTrailer(trailer);

        assertEquals(title, movie.getTitle());
        assertEquals(year, movie.getYear());
        assertEquals(fsk, movie.getFsk());
        assertEquals(shortDescription, movie.getShortDescription());
        assertEquals(description, movie.getDescription());
        assertEquals(trailer, movie.getTrailer());
    }
}
