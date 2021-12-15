package com.kinoticket.backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;

import com.kinoticket.backend.model.CinemaHall;
import com.kinoticket.backend.model.Seat;
import com.kinoticket.backend.repositories.CinemaHallRepository;
import com.kinoticket.backend.repositories.SeatsRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class CinemaHallServiceTest {

    @Autowired
    CinemaHallService cinemaHallService;
    @MockBean
    CinemaHallRepository cinemaHallRepository;
    @MockBean
    SeatsRepository seatsRepository;

    @Test
    void testAddCinemaHall() {
        CinemaHall cinemaHall = cinemaHallService.addCinemaHall(10, 10);

        assertNotNull(cinemaHall);
        assertEquals(cinemaHall.getScreenSize(), 10);
        assertEquals(cinemaHall.getSquareMeters(), 10);
    }

    @Test
    void testPutSeats() {
        CinemaHall cinemaHall = cinemaHallService.addCinemaHall(10, 10);

        assertNull(cinemaHall.getSeats());

        Seat s = new Seat();
        s.setRow(1);
        s.setSeatNumber(2);

        ArrayList<Seat> seats = new ArrayList<>();
        seats.add(s);

        cinemaHallService.putSeats(seats, cinemaHall);
        assertNotNull(cinemaHall.getSeats());

        assertEquals(1, cinemaHall.getSeats().size());
    }
}
