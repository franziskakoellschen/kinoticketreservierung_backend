package com.kinoticket.backend.rest;

import com.kinoticket.backend.model.FilmShow;
import com.kinoticket.backend.model.FilmShowDTO;
import com.kinoticket.backend.service.FilmShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/filmshows")
public class FilmShowController {

    @Autowired
    FilmShowService filmShowService;


    /*
    NOTE: This endpoint provides admin functionality
     */
    @PostMapping()
    public ResponseEntity<FilmShow> postFilmShow(@RequestBody FilmShowDTO filmShow) {
        FilmShow persistedFilmShow = filmShowService.postFilmShow(
            filmShow.getDate(), filmShow.getTime(), filmShow.getMovieId(), filmShow.getCinemaHallId()
        );

        if (persistedFilmShow == null) {
            log.error("Issue while persisting");
            return new ResponseEntity<FilmShow>(persistedFilmShow, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<FilmShow>(persistedFilmShow, HttpStatus.OK);
    }

    @GetMapping()
    public Iterable<FilmShow> getAllFilmShows() {
        return filmShowService.getAllFilmShows();
    }
}
