package com.kinoticket.backend.dto;

import java.sql.Time;
import java.util.Date;

public class FilmShowDTO {

    private Date date;
    private Time time;
    private long cinemaHallId;
    private long movieId;
    private String dimension;
    private String language;


    public String getLanguage() {
        return language;
    }

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

    public String getDimension() {
        return dimension;
    }
}
