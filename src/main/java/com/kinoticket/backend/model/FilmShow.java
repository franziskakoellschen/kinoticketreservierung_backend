package com.kinoticket.backend.model;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "FILMSHOW")
@NoArgsConstructor
@AllArgsConstructor
public class FilmShow {

    @Column
    @GeneratedValue
    @Id
    private long id;

    @Column
    private Date date;

    @Column
    private Time time;

    @ManyToOne(targetEntity = CinemaHall.class)
    @JoinColumn(name = "movie_id", referencedColumnName = "id")
    @JsonIgnore
    private Movie movie;

    @ManyToOne(targetEntity = CinemaHall.class)
    @JoinColumn(name = "cinema_hall_id", referencedColumnName = "id")
    @JsonIgnore
    private CinemaHall cinemaHall;

    @Column
    @OneToMany(mappedBy = "filmShow", cascade = CascadeType.ALL)
    private List<FilmShowSeat> filmShow_seats;

    public FilmShow (Date date, Time time, Movie movie, CinemaHall cinemaHall, List<FilmShowSeat> filmShow_seats) {
        this.date = date;
        this.time = time;
        this.movie = movie;
        this.cinemaHall = cinemaHall;
        this.filmShow_seats = filmShow_seats;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
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
