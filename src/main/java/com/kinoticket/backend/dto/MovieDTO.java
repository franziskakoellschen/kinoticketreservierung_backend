package com.kinoticket.backend.dto;

import java.util.Date;

import com.kinoticket.backend.model.Movie;

import lombok.Data;

@Data
public class MovieDTO {

    private long id;
    private String title;
    private int year;
    private String shortDescription;
    private String description;
    private int fsk;
    private String trailer;
    private String imageUrl;
    private String actors;
    private Date startDate;
    private int filmLength;
    private String originCountry;
    private String genre;
    private long image_id;

    public MovieDTO(Movie movie) {
        this.id = movie.getId();
        this.title = movie.getTitle();
        this.year = movie.getYear();
        this.shortDescription = movie.getShortDescription();
        this.description = movie.getDescription();
        this.fsk = movie.getFsk();
        this.trailer = movie.getTrailer();
        this.imageUrl = movie.getImageUrl();
        this.actors = movie.getActors();
        this.startDate = movie.getStartDate();
        this.filmLength = movie.getFilmLength();
        this.originCountry = movie.getOriginCountry();
        this.genre = movie.getGenre();
        this.image_id = movie.getImage_id();
    }

    public MovieDTO() {}

}
