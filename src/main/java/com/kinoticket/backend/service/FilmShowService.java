package com.kinoticket.backend.service;

import com.kinoticket.backend.model.*;
import com.kinoticket.backend.repositories.CinemaHallRepository;
import com.kinoticket.backend.repositories.FilmShowRepository;
import com.kinoticket.backend.repositories.FilmShowSeatRepository;
import com.kinoticket.backend.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException.BadRequest;

import java.sql.Date;
import java.sql.Time;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

@Service
public class FilmShowService {

    @Autowired
    FilmShowRepository filmShowRepository;

    @Autowired
    FilmShowSeatRepository filmShowSeatRepository;

    @Autowired
    CinemaHallRepository cinemaHallRepository;

    @Autowired
    MovieRepository movieRepository;

    public FilmShow postFilmShow(Date date, Time time, long movieId, long cinemaHallId){
        Optional<CinemaHall> cinemaHallR = cinemaHallRepository.findById(cinemaHallId);
        Optional<Movie> movieR = movieRepository.findById(movieId);
        if (cinemaHallR.isPresent() && movieR.isPresent()) {
            CinemaHall cinemaHall = cinemaHallR.get();
            Movie movie = movieR.get();
            FilmShow filmShow = new FilmShow(date, time, movie, cinemaHall, null);
            filmShow.setCinemaHall(cinemaHall);
            filmShowRepository.save(filmShow);

            // adding seats
            for (Seat s : cinemaHall.getSeats()) {
                FilmShowSeat filmShowSeat = new FilmShowSeat(s, filmShow, false);
                filmShowSeatRepository.save(filmShowSeat);
            }

            filmShow.setFilmShow_seats(
                filmShowSeatRepository.findByFilmShow_id(
                    filmShow.getId()
            ));
            return filmShow;
        }
        return null;
    }

    public FilmShow findById(long filmShowId) {
        return filmShowRepository.findById(filmShowId).get();
    }


    public Iterable<FilmShow> getAllFilmShows() {
        return filmShowRepository.findAll();
    }

}
