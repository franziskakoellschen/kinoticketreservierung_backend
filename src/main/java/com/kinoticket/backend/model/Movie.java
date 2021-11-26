package com.kinoticket.backend.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.lang.NonNull;

@Entity
@Table(name = "MOVIES")
public class Movie implements Serializable {

    @Column
    @NonNull
    @Id
    private String title;

    @Column
    @NonNull
    private int year;

    @Column
    @NonNull
    private String shortDescription;
    
    @Column
    private String description;
    
    @Column
    @NonNull
    private int fsk;

    @Column
    private String trailer;

    @OneToMany
    private List<FilmShow> filmShows;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getFsk() {
        return fsk;
    }

    public void setFsk(int fsk) {
        this.fsk = fsk;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public List<FilmShow> getFilmShows() {
        return filmShows;
    }

    public void setFilmShow(List<FilmShow> filmShows) {
        this.filmShows = filmShows;
    }
}
