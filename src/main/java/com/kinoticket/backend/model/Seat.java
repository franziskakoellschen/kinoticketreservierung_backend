package com.kinoticket.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "SEATS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Seat {

    @GeneratedValue
    @Id
    private int id;

    @Column
    private int row;

    @Column
    private int seatNumber;

    @ManyToOne(targetEntity = CinemaHall.class)
    @JoinColumn(name = "cinemahall_id", referencedColumnName = "id")
    @JsonIgnore
    private CinemaHall cinemaHall;

    @Column
    @OneToMany(mappedBy = "seat", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<FilmShowSeat> filmShow_seats;

    @Column
    @NotNull
    private int priceCategory;

    public int getRow() {
        return row;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public int getPriceCategory() {
        return priceCategory;
    }

    public void setPriceCategory(int priceCategory) {
        this.priceCategory = priceCategory;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public CinemaHall getCinemaHall() {
        return cinemaHall;
    }

    public void setCinemaHall(CinemaHall cinemaHall) {
        this.cinemaHall = cinemaHall;
    }

    public List<FilmShowSeat> getFilmShow_seats() {
        return filmShow_seats;
    }

    public void setFilmShow_seats(List<FilmShowSeat> filmShow_seats) {
        this.filmShow_seats = filmShow_seats;
    }
}
