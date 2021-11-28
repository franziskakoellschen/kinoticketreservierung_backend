package com.kinoticket.backend.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SeatTest {

    @Test
    void seatTest() {

        int row = 3;
        int seatNumber = 20;
        BookingPlan bookingPlan = new BookingPlan();
        boolean reserved = true;
        int priceCategory = 2;

        Seat seat = new Seat(row, seatNumber, bookingPlan, reserved, priceCategory);

        assertEquals(row, seat.getRow());
        assertEquals(seatNumber, seat.getSeatNumber());
        assertEquals(bookingPlan, seat.getBookingplan());
        assertEquals(bookingPlan.getId(), seat.getBookingplan().getId());
        assertEquals(bookingPlan.getSeats(), seat.getBookingplan().getSeats());
        assertEquals(reserved, seat.isReserved());
        assertEquals(priceCategory, seat.getPriceCategory());

        reserved = false;
        priceCategory = 1;

        seat.setReserved(reserved);
        seat.setPriceCategory(priceCategory);

        assertEquals(row, seat.getRow());
        assertEquals(seatNumber, seat.getSeatNumber());
        assertEquals(bookingPlan, seat.getBookingplan());
        assertEquals(bookingPlan.getId(), seat.getBookingplan().getId());
        assertEquals(bookingPlan.getSeats(), seat.getBookingplan().getSeats());
        assertEquals(reserved, seat.isReserved());
        assertEquals(priceCategory, seat.getPriceCategory());
    }
}
