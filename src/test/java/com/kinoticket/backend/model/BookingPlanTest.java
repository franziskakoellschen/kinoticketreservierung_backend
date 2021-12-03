package com.kinoticket.backend.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookingPlanTest {

    @Test
    void bookingPlanTest() {
        BookingPlan bookingPlan = new BookingPlan();

        int row1 = 3;
        int seatNumber1 = 5;
        boolean reserved1 = true;
        int priceCategory1 = 1;

        int row2 = 2;
        int seatNumber2 = 20;
        boolean reserved2 = false;
        int priceCategory2 = 3;


        Seat seat1 = new Seat(row1, seatNumber1, bookingPlan, reserved1, priceCategory1);
        Seat seat2 = new Seat(row2, seatNumber2, bookingPlan, reserved2, priceCategory2);

        List<Seat> seats = new ArrayList<>();
        seats.add(seat1);
        seats.add(seat2);

        bookingPlan.setSeats(seats);

        assertEquals(seats, bookingPlan.getSeats());
        assertEquals(row1, bookingPlan.getSeats().get(0).getRow());
        assertEquals(seatNumber1, bookingPlan.getSeats().get(0).getSeatNumber());
        assertEquals(bookingPlan, bookingPlan.getSeats().get(0).getBookingplan());
        assertEquals(reserved1, bookingPlan.getSeats().get(0).isReserved());
        assertEquals(priceCategory1, bookingPlan.getSeats().get(0).getPriceCategory());

        assertEquals(seats, bookingPlan.getSeats());
        assertEquals(row2, bookingPlan.getSeats().get(1).getRow());
        assertEquals(seatNumber2, bookingPlan.getSeats().get(1).getSeatNumber());
        assertEquals(bookingPlan, bookingPlan.getSeats().get(1).getBookingplan());
        assertEquals(reserved2, bookingPlan.getSeats().get(1).isReserved());
        assertEquals(priceCategory2, bookingPlan.getSeats().get(1).getPriceCategory());


    }

    @Test
    void setIdTest() {
        BookingPlan p = new BookingPlan();
        p.setId(5);
        assertEquals(5, p.getId());
    }

}
