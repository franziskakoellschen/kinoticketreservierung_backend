package com.kinoticket.backend.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

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

    @Test
    void seatPKEqualsTest() {

        SeatPK seatPK = new SeatPK();
        seatPK.row = 1;
        seatPK.seatNumber = 10;
        seatPK.bookingplan = 5;

        SeatPK seatPK2 = new SeatPK();
        seatPK2.row = 1;
        seatPK2.seatNumber = 10;
        seatPK2.bookingplan = 5;

        SeatPK seatPK3 = new SeatPK();
        seatPK3.row = 10;
        seatPK3.seatNumber = 10;
        seatPK3.bookingplan = 5;

        SeatPK seatPK4 = new SeatPK();
        seatPK4.row = 10;
        seatPK4.seatNumber = 10;
        seatPK4.bookingplan = 6;

        SeatPK seatPK5 = new SeatPK();
        seatPK5.row = 10;
        seatPK5.seatNumber = 11;
        seatPK5.bookingplan = 6;

        assertEquals(seatPK, seatPK);
        assertEquals(seatPK, seatPK2);
        assertNotEquals(seatPK, seatPK3);
        assertNotEquals(seatPK, seatPK4);
        assertNotEquals(seatPK, seatPK5);

        assertEquals(seatPK2, seatPK2);
        assertNotEquals(seatPK2, seatPK3);
        assertNotEquals(seatPK2, seatPK4);
        assertNotEquals(seatPK2, seatPK5);

        assertEquals(seatPK3, seatPK3);
        assertNotEquals(seatPK3, seatPK4);
        assertNotEquals(seatPK3, seatPK5);

        assertEquals(seatPK4, seatPK4);
        assertNotEquals(seatPK4, seatPK5);

        assertEquals(seatPK5, seatPK5);

        BookingPlan bookingPlan = new BookingPlan();

        assertNotEquals(seatPK, bookingPlan);
    }

    @Test
    void seatPKHashcodeTest() {

        SeatPK seatPK = new SeatPK();
        seatPK.row = 1;
        seatPK.seatNumber = 10;
        seatPK.bookingplan = 5;

        assertEquals(686469, seatPK.hashCode());

    }
}
