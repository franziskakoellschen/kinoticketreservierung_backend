package com.kinoticket.backend.model;

import java.sql.Date;
import java.sql.Time;

public class FilmShowDTO {

    private Date date;
    private Time time;
    private int cinemaHallId;

    public Date getDate() {
        return date;
    }

    public Time getTime() {
        return time;
    }

    public int getCinemaHallId() {
        return cinemaHallId;
    }

}
