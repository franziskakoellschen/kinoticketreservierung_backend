package com.kinoticket.backend.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TicketTests {
    @Test
    void ticketsTest() {

        Ticket ticket = new Ticket();

        FilmShow filmShow = new FilmShow();
        filmShow.setId(53252);

        double price = 10.2;

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

        Seat seat = new Seat();

        seat.setId(2);
        seat.setRow(5);
        seat.setSeatNumber(3);

        FilmShowSeat fss = new FilmShowSeat();
        fss.setSeat(seat);

        ticket.setFilmShow(filmShow);
        ticket.setMovie(movie);
        ticket.setFilmShowSeat(fss);
        ticket.setPrice(price);

        assertEquals(filmShow.getId(), ticket.getFilmShow().getId());
        assertEquals(movie, ticket.getMovie());
        assertEquals(fss, ticket.getFilmShowSeat());
        assertEquals(price, ticket.getPrice());

        assertEquals(title, ticket.getMovie().getTitle());
        assertEquals(year, ticket.getMovie().getYear());
        assertEquals(fsk, ticket.getMovie().getFsk());
        assertEquals(shortDescription, ticket.getMovie().getShortDescription());
        assertEquals(description, ticket.getMovie().getDescription());
        assertEquals(trailer, ticket.getMovie().getTrailer());

        assertTrue(true);

    }

    @Test
    void priceCategoriesTest() {
        Ticket t = new Ticket();
        t.setPriceForSeat();
        assertNull(t.getFilmShowSeat());
        assertEquals(t.getPrice(), 0);

        Seat s = new Seat();
        s.setPriceCategory(1);
        FilmShowSeat fss = new FilmShowSeat(s, new FilmShow());
        fss.setSeat(s);
        t.setFilmShowSeat(fss);
        t.setPriceForSeat();
        assertEquals(t.getPrice(), 9.0);

        t.getFilmShowSeat().getSeat().setPriceCategory(2);
        t.setPriceForSeat();
        assertEquals(t.getPrice(), 12.0);

        
        t.getFilmShowSeat().getSeat().setPriceCategory(3);
        t.setPriceForSeat();
        assertEquals(t.getPrice(), 14.0);

        
        t.getFilmShowSeat().getSeat().setPriceCategory(0);
        t.setPriceForSeat();
        assertEquals(t.getPrice(), 15.0);
    }
}
