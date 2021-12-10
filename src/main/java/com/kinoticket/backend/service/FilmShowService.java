package com.kinoticket.backend.service;

import com.kinoticket.backend.model.CinemaHall;
import com.kinoticket.backend.model.FilmShow;
import com.kinoticket.backend.model.FilmShowSeat;
import com.kinoticket.backend.model.Seat;
import com.kinoticket.backend.repositories.CinemaHallRepository;
import com.kinoticket.backend.repositories.FilmShowRepository;
import com.kinoticket.backend.repositories.FilmShowSeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;

import java.util.Optional;

@Service
public class FilmShowService {

    @Autowired
    FilmShowRepository filmShowRepository;

    @Autowired
    FilmShowSeatRepository filmShowSeatRepository;

    @Autowired
    CinemaHallRepository cinemaHallRepository;

    public void postFilmShow(Date date, Time time, long cinemaHallId){
        Optional<CinemaHall> cinemaHallR = cinemaHallRepository.findById(cinemaHallId);
        if (cinemaHallR.isPresent()) {
            CinemaHall cinemaHall = cinemaHallR.get();
            FilmShow filmShow = new FilmShow(date, time, cinemaHall, null);
            filmShow.setCinemaHall(cinemaHall);
            filmShowRepository.save(filmShow);
            for (Seat s : cinemaHall.getSeats()) {
                FilmShowSeat filmShowSeat = new FilmShowSeat(s, filmShow, false);
                filmShowSeatRepository.save(filmShowSeat);
            }
        }

    }
}
