package com.kinoticket.backend.rest;

import com.kinoticket.backend.model.FilmShowDTO;
import com.kinoticket.backend.service.FilmShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/filmshows")
public class FilmShowController {

    @Autowired
    FilmShowService filmShowService;


    /*
    NOTE: This endpoint provides admin functionality
     */
    @PostMapping()
    public void postMovie(@RequestBody FilmShowDTO filmShow) {
         filmShowService.postFilmShow(filmShow.getDate(), filmShow.getTime(), filmShow.getMovieId(), filmShow.getCinemaHallId());
    }
}
