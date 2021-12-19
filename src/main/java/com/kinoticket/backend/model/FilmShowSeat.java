package com.kinoticket.backend.model;

import java.util.Date;

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
    private FilmShowSeatStatus status;

    private double price;

    @Column
    private Date lastChanged;

    @PrePersist
    private void setPrice(){
        if(seat!=null){
            switch (seat.getPriceCategory()){
                case 1: this.price = 9.0D;break;
                case 2: this.price = 12.0D;break;
                case 3: this.price = 14.0D;break;
                default: this.price = 15.0D; break;
            }
        }
    }

    public FilmShowSeat(Seat seat, FilmShow filmShow) {
        this.seat = seat;
        this.filmShow = filmShow;
        this.status = FilmShowSeatStatus.FREE;
        this.lastChanged = new Date();
    }
}
