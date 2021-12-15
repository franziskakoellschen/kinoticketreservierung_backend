package com.kinoticket.backend.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SeatTest {

    @Test
    void seatTest() {

        CinemaHall cinemaHall = new CinemaHall( 20,80,100, null, null);

        int row = 3;
        int seatNumber = 20;
        boolean reserved = true;
        int priceCategory = 2;

        Seat seat = new Seat(1, row, seatNumber, cinemaHall, null, priceCategory);

        assertEquals(row, seat.getRow());
        assertEquals(seatNumber, seat.getSeatNumber());
        assertEquals(cinemaHall, seat.getCinemaHall());
        assertEquals(cinemaHall.getId(), seat.getCinemaHall().getId());
        assertEquals(cinemaHall.getSquareMeters(), seat.getCinemaHall().getSquareMeters());
        assertEquals(cinemaHall.getScreenSize(), seat.getCinemaHall().getScreenSize());
        assertEquals(priceCategory, seat.getPriceCategory());

        priceCategory = 1;

        seat.setPriceCategory(priceCategory);

        assertEquals(row, seat.getRow());
        assertEquals(seatNumber, seat.getSeatNumber());
        assertEquals(cinemaHall, seat.getCinemaHall());
        assertEquals(cinemaHall.getId(), seat.getCinemaHall().getId());
        assertEquals(cinemaHall.getSquareMeters(), seat.getCinemaHall().getSquareMeters());
        assertEquals(cinemaHall.getScreenSize(), seat.getCinemaHall().getScreenSize());
        assertEquals(priceCategory, seat.getPriceCategory());
    }

}
