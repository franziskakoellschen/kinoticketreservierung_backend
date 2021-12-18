package com.kinoticket.backend.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "FILMSHOW_SEAT")
@IdClass(FilmshowSeatPK.class)
@NoArgsConstructor

public class FilmShowSeat {

    @Id
    @ManyToOne(targetEntity = Seat.class)
    @JoinColumn(name = "seat_id", referencedColumnName = "id")
    private Seat seat;

    @Id
    @ManyToOne(targetEntity = FilmShow.class)
    @JoinColumn(name = "filmshow_id", referencedColumnName = "id")
    @JsonIgnore
    private FilmShow filmShow;

    @Column
    private boolean reserved;

    private double price;

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public FilmShow getFilmShow() {
        return filmShow;
    }

    public void setFilmShow(FilmShow filmShow) {
        this.filmShow = filmShow;
    }

    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }

    public FilmShowSeat(Seat seat, FilmShow filmShow, boolean reserved) {
        this.seat = seat;
        this.filmShow = filmShow;
        this.reserved = reserved;
    }

}
