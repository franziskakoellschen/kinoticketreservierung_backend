package com.kinoticket.backend.model;

import lombok.Data;

@Data
public class CinemaHallDTO {

    private long id;

    private int squareMeters;

    private int screenSize;

    public CinemaHallDTO(CinemaHall cinemaHall) {
        this.id = cinemaHall.getId();
        this.squareMeters = cinemaHall.getSquareMeters();
        this.screenSize = cinemaHall.getScreenSize();
    }

    public CinemaHallDTO() {}
}
