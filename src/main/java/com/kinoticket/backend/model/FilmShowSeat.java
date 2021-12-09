package com.kinoticket.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "FILMSHOW_SEAT")
@IdClass(FilmshowSeatPK.class)
@NoArgsConstructor
@AllArgsConstructor
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
}
