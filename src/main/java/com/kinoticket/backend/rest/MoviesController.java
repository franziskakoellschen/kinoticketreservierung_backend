package com.kinoticket.backend.rest;

import com.kinoticket.backend.model.Movie;
import com.kinoticket.backend.repositories.MovieRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MoviesController {
    @Autowired
    MovieRepository repository;

    public MoviesController() {}

    @GetMapping("/movies")
    public Iterable<Movie> getAll() {
        return repository.findAll();
    }

    @PostMapping("/movies")
    public Movie postMovie(@RequestBody Movie movie) {
        return repository.save(movie);
    }
}
