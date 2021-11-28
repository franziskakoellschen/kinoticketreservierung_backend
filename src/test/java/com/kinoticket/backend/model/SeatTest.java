package com.kinoticket.backend.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SeatTest {

    @Test
    void seatTest() {
        Seat seat = new Seat();
        int row = 3;
        int seatNumber = 20;
        BookingPlan bookingPlan = new BookingPlan();

        seat.setRow(row);
        seat.setSeatNumber(seatNumber);
        seat.setBookingplan(bookingPlan);

        assertEquals(row, seat.getRow());
        assertEquals(seatNumber, seat.getSeatNumber());
        assertEquals(bookingPlan, seat.getBookingplan());
        assertEquals(bookingPlan.getId(), seat.getBookingplan().getId());
        assertEquals(bookingPlan.getSeats(), seat.getBookingplan().getSeats());
    }
}
