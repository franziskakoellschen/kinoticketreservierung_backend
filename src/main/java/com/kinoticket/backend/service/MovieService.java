package com.kinoticket.backend.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
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
            movie.setFilmShows(this.getFilmShows(movie.getId()));
            if (movie.getFilmShows() != null) {
                movie.getFilmShows().sort(new FilmShowComparator());
            }
        });
        return movies;
    }

    public Movie getMovie(long id) {
        if (movieRepository.findById(id).isPresent()) {
            Movie movie = movieRepository.findById(id).get();
            movie.setFilmShows(this.getFilmShows(movie.getId()));
            return movie;
        } else {
            return null;
        }
    }

    public List<FilmShow> getFilmShows(long id) {
        if (movieRepository.findById(id).isPresent()) {
            LocalDateTime dateTime = LocalDateTime.now(ZoneId.of("CET"));
            List<FilmShow> filmShows =  filmShowRepository.findFutureFilmShowsByMovie(
                    id,
                    java.sql.Date.valueOf(dateTime.toLocalDate()),
                    java.sql.Time.valueOf(dateTime.toLocalTime())
            );
            if (filmShows != null) {
                filmShows.sort(new FilmShowComparator());
            }
            return filmShows;
        } else {
            return null;
        }
    }
}
