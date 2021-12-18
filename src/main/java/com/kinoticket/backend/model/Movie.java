package com.kinoticket.backend.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
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

    @Column(length=10485760)
    @NonNull
    private String shortDescription;
    
    @Column(length=10485760)
    private String description;

    @Column
    @NonNull
    private int fsk;

    @Column
    private String trailer;

    @Column
    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private List<FilmShow> filmShows;

    @Column
    private String imageUrl;

    @Column
    private String actors;

    @Column
    private Date startDate;

    @Column
    private int filmLength;

    @Column
    private String originCountry;

    @Column
    private String genre;

    @Column
    private long image_id;





}
