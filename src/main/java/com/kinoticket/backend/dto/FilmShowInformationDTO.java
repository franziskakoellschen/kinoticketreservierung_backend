package com.kinoticket.backend.dto;

import java.sql.Time;
import java.util.Date;
import java.util.List;

import com.kinoticket.backend.model.FilmShow;
import com.kinoticket.backend.model.FilmShowSeat;

import lombok.Data;

@Data
public class FilmShowInformationDTO {

    private long id;
    private Date date;
    private Time time;

    private MovieDTO movie;
    private CinemaHallDTO cinemaHall;

    private List<List<FilmShowSeat>> filmShowSeats;

    public FilmShowInformationDTO() {}

    public FilmShowInformationDTO(FilmShow filmShow) {
        this.id = filmShow.getId();
        this.date = filmShow.getDate();
        this.time = filmShow.getTime();
        this.movie = new MovieDTO(filmShow.getMovie());
        this.cinemaHall = new CinemaHallDTO(filmShow.getCinemaHall());
    }
}
