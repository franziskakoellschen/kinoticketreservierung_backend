package com.kinoticket.backend.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FilmShowSeatTest {

    @Test
    void filmShowSeatTest() {
        int row = 10;
        int seatNumber = 25;
        int priceCategory = 1;
        Seat seat = new Seat(5, row, seatNumber, null, null, priceCategory);
        FilmShow filmShow = new FilmShow();
        FilmShowSeat filmShowSeat = new FilmShowSeat(seat, filmShow);
        assertEquals(row, filmShowSeat.getSeat().getRow());
        assertEquals(seatNumber, filmShowSeat.getSeat().getSeatNumber());
        assertEquals(priceCategory, filmShowSeat.getSeat().getPriceCategory());
        assertEquals(FilmShowSeatStatus.FREE, filmShowSeat.getStatus());
    }

    @Test
    void filmShowSeatPKEqualsTest() {

        FilmshowSeatPK filmshowSeatPK = new FilmshowSeatPK();
        filmshowSeatPK.filmShow = 1;
        filmshowSeatPK.seat = 20;

        FilmshowSeatPK filmShowSeatPK2 = new FilmshowSeatPK();
        filmShowSeatPK2.filmShow = 1;
        filmShowSeatPK2.seat = 20;

        FilmshowSeatPK filmshowSeatPK3 = new FilmshowSeatPK();
        filmshowSeatPK3.filmShow = 2;
        filmshowSeatPK3.seat = 20;

        FilmshowSeatPK filmshowSeatPK4 = new FilmshowSeatPK();
        filmshowSeatPK4.filmShow = 1;
        filmshowSeatPK4.seat = 15;

        FilmshowSeatPK filmshowSeatPK5 = new FilmshowSeatPK();
        filmshowSeatPK5.filmShow = 2;
        filmshowSeatPK5.seat = 10;

        assertEquals(filmshowSeatPK, filmShowSeatPK2);
        assertNotEquals(filmshowSeatPK, filmshowSeatPK3);
        assertNotEquals(filmshowSeatPK, filmshowSeatPK4);
        assertNotEquals(filmshowSeatPK, filmshowSeatPK5);

        Seat seat = new Seat();

        assertFalse(filmshowSeatPK.equals(seat));
    }

    @Test
    void seatPKHashcodeTest() {

        FilmshowSeatPK filmshowSeatPK = new FilmshowSeatPK();
        filmshowSeatPK.filmShow = 1;
        filmshowSeatPK.seat = 20;

        assertEquals(22724, filmshowSeatPK.hashCode());

    }
}
