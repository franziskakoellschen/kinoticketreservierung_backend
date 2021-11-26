package com.kinoticket.backend.service;

import com.kinoticket.backend.model.Movie;
import com.kinoticket.backend.repositories.FilmShowRepository;
import com.kinoticket.backend.repositories.MovieRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieService {
    @Autowired
    MovieRepository movieRepository;

    @Autowired
    FilmShowRepository filmShowRepository;

    public Movie addMovie(Movie movie) {
        if (movie.getFilmShows() != null) {
            filmShowRepository.saveAll(movie.getFilmShows());
        }
        return movieRepository.save(movie);
    }

    public Iterable<Movie> getAll() {
        return movieRepository.findAll();
    }
}
