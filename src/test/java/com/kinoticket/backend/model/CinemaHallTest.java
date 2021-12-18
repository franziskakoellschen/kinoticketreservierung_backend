package com.kinoticket.backend.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CinemaHallTest {

    @Test
    void cinemaHallTest() {

        CinemaHall cinemaHall = new CinemaHall();

        int squareMeters = 50;
        int screenSize = 60;

        cinemaHall.setSquareMeters(squareMeters);
        cinemaHall.setScreenSize(screenSize);

        Seat seat1 = new Seat(5, 10, 20, cinemaHall, null, 1);
        Seat seat2 = new Seat(6, 15, 21, cinemaHall, null, 2);

        List<Seat> seats = new ArrayList<>();
        seats.add(seat1);
        seats.add(seat2);

        cinemaHall.setSeats(seats);

        assertEquals(squareMeters, cinemaHall.getSquareMeters());
        assertEquals(screenSize, cinemaHall.getScreenSize());

        assertEquals(seats, cinemaHall.getSeats());
        assertEquals(seat1, cinemaHall.getSeats().get(0));
        assertEquals(seat2.getId(), cinemaHall.getSeats().get(1).getId());

    }

}
