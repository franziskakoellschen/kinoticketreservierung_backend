package com.kinoticket.backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;

import com.kinoticket.backend.model.FilmShow;
import com.kinoticket.backend.model.Movie;
import com.kinoticket.backend.repositories.FilmShowRepository;
import com.kinoticket.backend.repositories.MovieRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class MovieServiceTest {
    
    @MockBean
    MovieRepository movieRepository;

    @MockBean
    FilmShowRepository filmShowRepository;

    @Autowired
    MovieService movieService;

    @Test
    void testGetFilmShows() {

        Movie m = new Movie();
        m.setId(4711);
        ArrayList<FilmShow> shows = new ArrayList<FilmShow>();

        shows.add(new FilmShow());
        shows.add(new FilmShow());

        m.setFilmShows(shows);

        movieService.postMovie(m);

        int count = 0;
        when(movieRepository.findById(4711L)).thenReturn(Optional.of(m));
        when(filmShowRepository.findFutureFilmShowsByMovie(eq(4711L), any(), any())).thenReturn(shows);
        Iterator<FilmShow> iterator = movieService.getFilmShows(4711).iterator();
        while(iterator.hasNext()) {
            count++;
            iterator.next();
        }
        assertEquals(2, count);
    }

    @Test
    void testGetMovie() {
        Movie m1 = new Movie();
        m1.setId(4711);
        m1.setTitle("Foo");
        m1.setYear(2000);
        movieService.postMovie(m1);

        when(movieRepository.findById(4711l)).thenReturn(Optional.of(m1));
        assertTrue(movieRepository.findById(4711l).isPresent());
        Movie m = movieService.getMovie(4711);
        assertNotNull(m);
    }

    @Test
    void testGetNonPresentMovie() {
        assertNull(movieService.getMovie(4711));
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
        
        Movie m1 = new Movie();
        m1.setTitle("Foo");
        Movie retMovie1 = movieService.postMovie(m1);

        Movie m2 = new Movie();
        m2.setTitle("Foo");
        Movie retMovie2 = movieService.postMovie(m2);

        assertEquals(retMovie1, retMovie2);
    }
}
