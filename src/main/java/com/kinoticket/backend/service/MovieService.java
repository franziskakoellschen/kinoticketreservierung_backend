package com.kinoticket.backend.service;

import com.kinoticket.backend.model.FilmShow;
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

    public Movie postMovie(Movie movie) {
        if (movie.getFilmShows() != null) {
            filmShowRepository.saveAll(movie.getFilmShows());
        }
        return movieRepository.save(movie);
    }

    public Iterable<Movie> getMovies() {
        return movieRepository.findAll();
    }

    public Movie getMovie(long id) {
        if (movieRepository.findById(id).isPresent()) {
            return movieRepository.findById(id).get();
        } else {
            return null;
        }
    }

    public Iterable<FilmShow> getFilmShows(long id) {
        if (movieRepository.findById(id).isPresent()) {
            return movieRepository.findById(id).get().getFilmShows();
        } else {
            return null;
        }
    }
}
