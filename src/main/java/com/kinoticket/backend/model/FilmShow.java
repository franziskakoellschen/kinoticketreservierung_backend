package com.kinoticket.backend.model;

import java.util.Date;
import java.sql.Time;

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

    @Column
    private String dimension;

    @Column
    private String language;

    @ManyToOne(targetEntity = Movie.class)
    @JoinColumn(name = "movie_id", referencedColumnName = "id")
    @JsonIgnore
    private Movie movie;

    @ManyToOne(targetEntity = CinemaHall.class)
    @JoinColumn(name = "cinema_hall_id", referencedColumnName = "id")
    @JsonIgnore
    private CinemaHall cinemaHall;

    public FilmShow (Date date, Time time, Movie movie, CinemaHall cinemaHall, String dimension, String language) {
        this.date = date;
        this.time = time;
        this.movie = movie;
        this.cinemaHall = cinemaHall;
        this.dimension = dimension;
        this.language = language;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
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

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }
}
