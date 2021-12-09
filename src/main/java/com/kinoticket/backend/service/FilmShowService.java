package com.kinoticket.backend.service;

import com.kinoticket.backend.model.CinemaHall;
import com.kinoticket.backend.model.FilmShow;
import com.kinoticket.backend.model.FilmShowSeat;
import com.kinoticket.backend.model.Seat;
import com.kinoticket.backend.repositories.CinemaHallRepository;
import com.kinoticket.backend.repositories.FilmShowRepository;
import com.kinoticket.backend.repositories.FilmShowSeatRepository;
import com.kinoticket.backend.repositories.SeatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FilmShowService {

    @Autowired
    FilmShowRepository filmShowRepository;

    @Autowired
    FilmShowSeatRepository filmShowSeatRepository;

    @Autowired
    SeatsRepository seatsRepository;

    @Autowired
    CinemaHallRepository cinemaHallRepository;

    public void postFilmShow(Date date, Time time, int cinemaHallId){
        FilmShow filmShow = new FilmShow(date, time, null, null);
        Optional<CinemaHall> cinemaHallR = cinemaHallRepository.findById(cinemaHallId);
        if (cinemaHallR.isPresent()) {
            CinemaHall cinemaHall = cinemaHallR.get();
            filmShow.setCinemaHall(cinemaHall);
            List<FilmShowSeat> filmShowSeatList = new ArrayList<>();
            for (Seat s : cinemaHall.getSeats()) {
                FilmShowSeat filmShowSeat = new FilmShowSeat(s, filmShow, false);
                filmShowSeatList.add(filmShowSeat);
            }
            filmShow.setFilmShow_seats(filmShowSeatList);
            filmShowRepository.save(filmShow);
        }

    }
}
