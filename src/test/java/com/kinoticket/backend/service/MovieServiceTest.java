package com.kinoticket.backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;

import com.kinoticket.backend.model.FilmShow;
import com.kinoticket.backend.model.Movie;
import com.kinoticket.backend.repositories.FilmShowRepository;
import com.kinoticket.backend.repositories.MovieRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MovieServiceTest {
    
    @Autowired
    MovieRepository movieRepository;

    @Autowired
    FilmShowRepository filmShowRepository;

    @Autowired
    MovieService movieService;

    @BeforeEach
    void beforeEach() {
        if (movieRepository.findById("Foo").isPresent()) {
            movieRepository.delete(movieRepository.findById("Foo").get());
        }
    }

    @AfterEach
    void afterEach() {
        if (movieRepository.findById("Foo").isPresent()) {
            movieRepository.delete(movieRepository.findById("Foo").get());
        }
    }

    @Test
    void testGetFilmShows() {

        long oldFilmShowSize = filmShowRepository.count();

        Movie m = new Movie();
        m.setTitle("Foo");
        ArrayList<FilmShow> shows = new ArrayList<FilmShow>();

        shows.add(new FilmShow());
        shows.add(new FilmShow());

        m.setFilmShows(shows);

        movieService.postMovie(m);

        assertEquals(oldFilmShowSize+2, filmShowRepository.count());

        int count = 0;
        Iterator<FilmShow> iterator = movieService.getFilmShows("Foo").iterator();
        while(iterator.hasNext()) {
            count++;
            iterator.next();
        }
        assertEquals(count, 2);
    }

    @Test
    void testGetMovie() {
        Movie m1 = new Movie();
        m1.setTitle("Foo");
        m1.setYear(2000);
        movieService.postMovie(m1);

        assertTrue(movieRepository.findById("Foo").isPresent());
        Movie m = movieService.getMovie("Foo");
        assertNotNull(m);
    }

    @Test
    void testGetNonPresentMovie() {
        assertNull(movieService.getMovie("Foo"));
    }

    @Test
    void testGetMovies() {
        int count = 0;
        Iterator<Movie> iterator = movieService.getMovies().iterator();
        while(iterator.hasNext()) {
            count++;
            iterator.next();
        }

        assertEquals(movieRepository.count(), count);
    }

    @Test
    void testPostMovie() {
        long repoSize = movieRepository.count();

        Movie m1 = new Movie();
        m1.setTitle("Foo");
        movieService.postMovie(m1);

        assertEquals(movieRepository.count(), repoSize+1);

        Movie m2 = new Movie();
        m2.setTitle("Foo");
        movieService.postMovie(m2);

        assertEquals(movieRepository.count(), repoSize+1);
    }
}
