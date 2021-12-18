package com.kinoticket.backend.service;

import java.util.List;

import com.kinoticket.backend.model.CinemaHall;
import com.kinoticket.backend.model.Seat;
import com.kinoticket.backend.repositories.CinemaHallRepository;
import com.kinoticket.backend.repositories.SeatsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CinemaHallService {

    @Autowired
    CinemaHallRepository cinemaHallRepository;

    @Autowired
    SeatsRepository seatsRepository;

    public CinemaHall addCinemaHall(int squareMeters, int screenSize) {
        CinemaHall ch = new CinemaHall();

        ch.setScreenSize(screenSize);
        ch.setSquareMeters(squareMeters);

        cinemaHallRepository.save(ch);
        return ch;
    }

    public CinemaHall putSeats(List<Seat> seats, CinemaHall cinemaHall) {

        for (Seat seat : seats) {
            seat.setCinemaHall(cinemaHall);
        }

        seatsRepository.saveAll(seats);
        cinemaHall.setSeats(seats);
        cinemaHallRepository.save(cinemaHall);

        return cinemaHall;
    }

}
