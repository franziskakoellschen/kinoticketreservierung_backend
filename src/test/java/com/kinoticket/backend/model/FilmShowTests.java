package com.kinoticket.backend.model;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class FilmShowTests {
    @Test
    void filmShowTests() {
        assertEquals(new FilmShow(), new FilmShow());
        assertEquals(new FilmShow().hashCode(), new FilmShow().hashCode());
        assertEquals(new FilmShow().toString(), "FilmShow(id=0, date=null, time=null, seatingPlan=null)");
    }
}
