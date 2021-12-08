package com.kinoticket.backend.service;

import java.util.List;

import com.kinoticket.backend.model.FilmShow;
import com.kinoticket.backend.model.Movie;
import com.kinoticket.backend.repositories.FilmShowRepository;
import com.kinoticket.backend.repositories.MovieRepository;
import com.kinoticket.backend.util.FilmShowComparator;

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
        Iterable<Movie> movies = movieRepository.findAll();
        movies.forEach(movie -> {
            if (movie.getFilmShows() != null) {
                movie.getFilmShows().sort(new FilmShowComparator());
            }
        });
        return movies;
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
            List<FilmShow> filmShows = movieRepository.findById(id).get().getFilmShows();
            if (filmShows != null) {
                filmShows.sort(new FilmShowComparator());
            }
            return filmShows;
        } else {
            return null;
        }
    }
}
