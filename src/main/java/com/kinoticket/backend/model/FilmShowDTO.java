package com.kinoticket.backend.model;

import java.sql.Time;
import java.util.Date;

public class FilmShowDTO {

    private Date date;
    private Time time;
    private long cinemaHallId;
    private long movieId;

    public Date getDate() {
        return date;
    }

    public Time getTime() {
        return time;
    }

    public long getCinemaHallId() {
        return cinemaHallId;
    }

    public long getMovieId() {
        return movieId;
    }
}
