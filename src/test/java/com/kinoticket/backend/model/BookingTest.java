package com.kinoticket.backend.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookingTest {

    @Test
    void bookingTest() {
        Booking booking = new Booking();

         String meansOfPayment = "Visa";
         boolean isPaid = true;
         boolean isActive = true;
         long customerID = 344646l;


        Ticket ticket = new Ticket();

        FilmShow filmShow = new FilmShow();
        filmShow.setId(53252);

        FilmShowSeat fss = new FilmShowSeat();
        Seat seat = new Seat();

        seat.setId(2);
        seat.setRow(5);
        seat.setSeatNumber(3);
        fss.setSeat(seat);

        double price= 10.2;

        Movie movie = new Movie();

        String title = "Test Movie";
        int year = 2000;
        int fsk = 12;
        String shortDescription = "This is a Description";
        String description = "This is a full Description";
        String trailer = "someTrailerUrl";

        movie.setTitle(title);
        movie.setYear(year);
        movie.setFsk(fsk);
        movie.setShortDescription(shortDescription);
        movie.setDescription(description);
        movie.setTrailer(trailer);

        ticket.setFilmShow(filmShow);
        ticket.setMovie(movie);
        ticket.setFilmShowSeat(fss);
        ticket.setPrice(price);
        List<Ticket> ticketList = new ArrayList<>();
        ticketList.add(ticket);

        booking.setMeansOfPayment(meansOfPayment);
        booking.setPaid(isPaid);
        booking.setActive(isActive);
        booking.setCustomerId(customerID);
        booking.setTickets(ticketList);

        assertEquals(meansOfPayment, booking.getMeansOfPayment());
        assertEquals(isPaid, booking.isPaid());
        assertEquals(isActive, booking.isActive());
        assertEquals(customerID, booking.getCustomerId());

        assertEquals(filmShow.getId(), booking.getTickets().get(0).getFilmShow().getId());
        assertEquals(movie, booking.getTickets().get(0).getMovie());
        assertEquals(fss, booking.getTickets().get(0).getFilmShowSeat());
        assertEquals(price, booking.getTickets().get(0).getPrice());

        assertEquals(title, booking.getTickets().get(0).getMovie().getTitle());
        assertEquals(year, booking.getTickets().get(0).getMovie().getYear());
        assertEquals(fsk, booking.getTickets().get(0).getMovie().getFsk());
        assertEquals(shortDescription, booking.getTickets().get(0).getMovie().getShortDescription());
        assertEquals(description, booking.getTickets().get(0).getMovie().getDescription());
        assertEquals(trailer, booking.getTickets().get(0).getMovie().getTrailer());


    }

    @Test
    void ConstructorTest() {
        new Booking(2L, false, 1234);
    }

}
