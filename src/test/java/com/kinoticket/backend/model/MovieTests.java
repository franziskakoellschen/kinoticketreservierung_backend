package com.kinoticket.backend.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

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

    @Test
    void equalsTest() {
        Movie movieA = new Movie("Test Movie", 2000, "This is a Description", "This is a full Description", 16, "someTrailerUrl", new ArrayList<FilmShow>());
        Movie movieB = new Movie();

        String title = "Test Movie";
        int year = 2000;
        int fsk = 16;
        String shortDescription = "This is a Description";
        String description = "This is a full Description";
        String trailer = "someTrailerUrl";
        ArrayList<FilmShow> filmShows = new ArrayList<FilmShow>();

        movieB.setTitle(title);
        movieB.setYear(year);
        movieB.setFsk(fsk);
        movieB.setShortDescription(shortDescription);
        movieB.setDescription(description);
        movieB.setTrailer(trailer);
        movieB.setFilmShows(filmShows);

        assertEquals(movieA, movieB);
        assertEquals(movieA.hashCode(), movieB.hashCode());
    }

    @Test
    void toStringTest() {
        Movie movieA = new Movie("Test Movie", 2000, "This is a Description", "This is a full Description", 16, "someTrailerUrl", new ArrayList<FilmShow>());
        assertEquals("Movie(title=Test Movie, year=2000, shortDescription=This is a Description, description=This is a full Description, fsk=16, trailer=someTrailerUrl, filmShows=[])", movieA.toString());
    }
}
