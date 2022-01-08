package com.kinoticket.backend.rest;

import com.kinoticket.backend.model.FilmShow;
import com.kinoticket.backend.model.Movie;
import com.kinoticket.backend.service.MovieService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MoviesController {

    @Autowired
    MovieService service;

    @GetMapping()
    public Iterable<Movie> getMovies() {
        return service.getMovies();
    }

    @PostMapping()
    public Movie postMovie(@RequestBody Movie movie) {
        return service.postMovie(movie);
    }

    @GetMapping("/{id}")
    public Movie getMovie(@PathVariable long id) {
        return service.getMovie(id);
    }

    @GetMapping("/{movieId}/filmShows")
    public List<FilmShow> getFilmShows(@PathVariable long movieId) {
        return service.getFilmShows(movieId);
    }


}


