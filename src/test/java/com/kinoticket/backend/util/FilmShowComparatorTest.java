package com.kinoticket.backend.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

import com.kinoticket.backend.model.FilmShow;

import org.junit.jupiter.api.Test;

public class FilmShowComparatorTest {
    @Test
    void testCompare() {
        
        ArrayList<FilmShow> filmShows = new ArrayList<FilmShow>();

        FilmShow show1 = new FilmShow();
        show1.setId(1);
        show1.setDate(Date.valueOf("2021-12-08"));
        show1.setTime(Time.valueOf("19:00:00"));
        FilmShow show2 = new FilmShow();
        show2.setId(2);
        show2.setDate(Date.valueOf("2021-12-07"));
        show2.setTime(Time.valueOf("19:00:00"));
        FilmShow show3 = new FilmShow();
        show3.setId(3);
        show3.setDate(null);
        show3.setTime(null);
        FilmShow show4 = new FilmShow();
        show4.setId(4);
        show4.setDate(Date.valueOf("2021-12-07"));
        show4.setTime(Time.valueOf("18:00:00"));

        filmShows.add(show1);
        filmShows.add(show2);
        filmShows.add(show3);
        filmShows.add(show4);

        filmShows.sort(new FilmShowComparator());

        assertEquals(4, filmShows.get(0).getId());
        assertEquals(2, filmShows.get(1).getId());
        assertEquals(1, filmShows.get(2).getId());
        assertEquals(3, filmShows.get(3).getId());

    }
}
