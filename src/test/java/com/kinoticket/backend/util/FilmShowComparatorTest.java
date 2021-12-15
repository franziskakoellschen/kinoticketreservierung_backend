package com.kinoticket.backend.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.kinoticket.backend.model.FilmShow;

import org.junit.jupiter.api.Test;

public class FilmShowComparatorTest {
    @Test
    void testCompare() throws ParseException {
        
        ArrayList<FilmShow> filmShows = new ArrayList<FilmShow>();

        FilmShow show1 = new FilmShow();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        show1.setId(1);
        show1.setDate(dateFormat.parse("2021-12-08 00:00:00"));
        show1.setTime(Time.valueOf("19:00:00"));
        FilmShow show2 = new FilmShow();
        show2.setId(2);
        show2.setDate(dateFormat.parse("2021-12-07 00:00:00"));
        show2.setTime(Time.valueOf("19:00:00"));
        FilmShow show3 = new FilmShow();
        show3.setId(3);
        show3.setDate(null);
        show3.setTime(null);
        FilmShow show4 = new FilmShow();
        show4.setId(4);
        show4.setDate(dateFormat.parse("2021-12-07 00:00:00"));
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
