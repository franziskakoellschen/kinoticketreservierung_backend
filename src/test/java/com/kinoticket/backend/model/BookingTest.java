package com.kinoticket.backend.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookingTest {

    @Test
    void bookingTest() {
        Booking booking = new Booking();

        int id = 000111252021;

        booking.setId(id);

        assertEquals(booking.getId(), id);

    }

}
