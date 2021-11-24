package com.kinoticket.backend.rest;

import com.kinoticket.backend.model.FilmShow;
import com.kinoticket.backend.model.Movie;
import com.kinoticket.backend.service.MoviesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movies")
public class MoviesController {

    @Autowired
    MoviesService service;

    @PostMapping()
    public Movie postMovie(@RequestBody Movie movie) {
        return service.putMovie(movie);
    }

    @GetMapping()
    public Iterable<Movie> getMovies() {
        return service.getMovies();
    }


    @GetMapping("/{id}")
    public Movie getMovie(@PathVariable int id) {
        return service.getMovie(id);
    }

    @GetMapping("/{movieId}/filmShows")
    public Iterable<FilmShow> getFilmShows(@PathVariable int movieId) {
        return service.getFilmShows(movieId);
    }
}
