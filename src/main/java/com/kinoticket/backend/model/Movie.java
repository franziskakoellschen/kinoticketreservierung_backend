package com.kinoticket.backend.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.lang.NonNull;

import lombok.Data;

@Entity
@Table(name = "MOVIES")
@Data
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
}
