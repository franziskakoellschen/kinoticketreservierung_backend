package com.kinoticket.backend.service;

import com.kinoticket.backend.model.FilmShow;
import com.kinoticket.backend.model.Movie;
import com.kinoticket.backend.repositories.MovieRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MoviesService {

    @Autowired
    MovieRepository repository;

    public Movie putMovie(Movie movie) {
        // TODO
        return movie;
    }

    public Iterable<Movie> getMovies() {
        // TODO
        return null;
    }

    public Movie getMovie(int id) {
        // TODO
        return null;
    }

    public Iterable<FilmShow> getFilmShows(int movieId) {
        // TODO
        return null;
    }
}
