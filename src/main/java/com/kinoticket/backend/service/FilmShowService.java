package com.kinoticket.backend.service;

import java.sql.Date;
import java.sql.Time;
import java.util.Optional;

import com.kinoticket.backend.model.CinemaHall;
import com.kinoticket.backend.model.FilmShow;
import com.kinoticket.backend.model.FilmShowInformationDTO;
import com.kinoticket.backend.model.FilmShowSeat;
import com.kinoticket.backend.model.Movie;
import com.kinoticket.backend.model.Seat;
import com.kinoticket.backend.repositories.CinemaHallRepository;
import com.kinoticket.backend.repositories.FilmShowRepository;
import com.kinoticket.backend.repositories.FilmShowSeatRepository;
import com.kinoticket.backend.repositories.MovieRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            FilmShow filmShow = new FilmShow(date, time, movie, cinemaHall);
            filmShow.setCinemaHall(cinemaHall);
            filmShowRepository.save(filmShow);

            // persisting seats
            for (Seat s : cinemaHall.getSeats()) {
                FilmShowSeat filmShowSeat = new FilmShowSeat(s, filmShow, false);
                filmShowSeatRepository.save(filmShowSeat);
            }

            return filmShow;
        }
        return null;
    }

    public FilmShow findById(long filmShowId) {
        return filmShowRepository.findById(filmShowId).get();
    }

    public Optional<FilmShowInformationDTO> getFilmShowInformation(long filmShowId) {
        if (filmShowRepository.findById(filmShowId).isPresent()) {
            return Optional.of(new FilmShowInformationDTO(
                filmShowRepository.findById(filmShowId).get())
            );
        }
        return Optional.empty();
    }

    public Iterable<FilmShow> getAllFilmShows() {
        return filmShowRepository.findAll();
    }

}
