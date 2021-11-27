package com.kinoticket.backend.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Data
@NoArgsConstructor
@Table(name = "MOVIES")
@NoArgsConstructor
@AllArgsConstructor
public class Movie implements Serializable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

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
}
