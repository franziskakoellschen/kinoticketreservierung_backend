package com.kinoticket.backend.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TicketTests {
    @Test
    void ticketsTest() {
        Ticket ticket = new Ticket();

        String filmShowId = "53252";
        String seat ="5b";
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


        ticket.setFilmShow(new FilmShow());
        ticket.setMovie(movie);
        ticket.setSeat(seat);
        ticket.setPrice(price);


        assertEquals(filmShowId, ticket.getFilmShow());
        assertEquals(movie, ticket.getMovie());
        assertEquals(seat, ticket.getSeat());
        assertEquals(price, ticket.getPrice());

        assertEquals(title, ticket.getMovie().getTitle());
        assertEquals(year, ticket.getMovie().getYear());
        assertEquals(fsk, ticket.getMovie().getFsk());
        assertEquals(shortDescription, ticket.getMovie().getShortDescription());
        assertEquals(description, ticket.getMovie().getDescription());
        assertEquals(trailer, ticket.getMovie().getTrailer());

    }

}
