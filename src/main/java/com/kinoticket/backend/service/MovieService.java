package com.kinoticket.backend.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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
            List<FilmShow> filmShows = filmShowRepository.findFutureFilmShowsByMovie(
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

    public List<FilmShow> getFilmShowsWithFilters(long id, Date date1, Date date2, String dimension , String language) {


        if (movieRepository.findById(id).isPresent()) {
                LocalDateTime dateTime = LocalDateTime.now(ZoneId.of("CET"));
                List<FilmShow> filmShows = new ArrayList<>();
                if(date1 == null && date2 == null){

                    filmShows = filmShowRepository.findFutureFilmShowsByMovieWithFilter(
                            id,
                            java.sql.Date.valueOf(dateTime.toLocalDate()),
                            java.sql.Time.valueOf(dateTime.toLocalTime()),
                            dimension,
                            language);
                }
               else if(date1 != null && date2 == null){
                    java.sql.Date sqlDate1 = new java.sql.Date(date1.getTime());
                    filmShows = filmShowRepository.findFilmShowsWithFromDate(
                            id,
                            sqlDate1,
                            dimension,
                            language
                    );
                }
                    else if(date1 == null && date2 != null){
                    java.sql.Date sqlDate2 = new java.sql.Date(date2.getTime());
                    filmShows = filmShowRepository.findFilmShowsWithTwoDates(
                            id,
                            java.sql.Date.valueOf(dateTime.toLocalDate()),
                            sqlDate2,
                            dimension,
                            language
                    );
                    }

                else{
                    java.sql.Date sqlDate1 = new java.sql.Date(date1.getTime());
                    java.sql.Date sqlDate2 = new java.sql.Date(date2.getTime());
                    filmShows = filmShowRepository.findFilmShowsWithTwoDates(
                            id,
                            sqlDate1,
                            sqlDate2,
                            dimension,
                            language
                    );
                }

            if (filmShows != null) {
                filmShows.sort(new FilmShowComparator());
            }
            return filmShows;
        } else {
            return null;
        }
    }

    public Iterable<Movie> getMoviesWithFilters(Date date1, Date date2, String genre, String dimension, String language) {
        Iterable<Movie> movies = movieRepository.findMovieWithFilters(genre);
        Iterator<Movie> movieIterator = movies.iterator();

        while (movieIterator.hasNext()) {
            Movie movie = movieIterator.next();
            movie.setFilmShows(this.getFilmShowsWithFilters(movie.getId(),date1,date2 ,dimension , language));
            if (movie.getFilmShows().size() != 0) {
                movie.getFilmShows().sort(new FilmShowComparator());
            } else {
                movieIterator.remove();
            }

        }
        return movies;
    }

}
