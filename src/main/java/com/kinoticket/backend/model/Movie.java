package com.kinoticket.backend.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.lang.NonNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "MOVIES")
@NoArgsConstructor
@AllArgsConstructor
public class Movie implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    @NonNull
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

    @Column
    private String imageUrl;
}
