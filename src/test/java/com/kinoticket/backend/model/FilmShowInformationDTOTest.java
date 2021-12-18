package com.kinoticket.backend.model;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class FilmShowInformationDTOTest {

    @Test
    void testFilmShowInformationDTO() {

        FilmShow filmShow = new FilmShow();
        filmShow.setCinemaHall(new CinemaHall());
        filmShow.setMovie(new Movie());

        assertNotNull(
            new FilmShowInformationDTO(filmShow)
        );
    }
}
