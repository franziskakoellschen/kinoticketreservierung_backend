package com.kinoticket.backend.rest;

import com.kinoticket.backend.model.FilmShowSeat;
import com.kinoticket.backend.service.FilmShowSeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/filmshows/{filmshowId}/seats")
public class FilmShowSeatController {

    @Autowired
    FilmShowSeatService filmShowSeatService;

    @GetMapping()
    public List<FilmShowSeat> getMovies(@PathVariable(value= "filmshowId") int filmShowId) {
        return filmShowSeatService.getFilmShowSeats(filmShowId);
    }

    @PutMapping()
    public ResponseEntity<FilmShowSeat> updateFilmShowSeat(@PathVariable(value = "filmshowId") int filmShowId,
                                                           @RequestBody FilmShowSeat updatedFilmShowSeat
    ) {
        return filmShowSeatService.changeSeat(updatedFilmShowSeat);
    }

}
