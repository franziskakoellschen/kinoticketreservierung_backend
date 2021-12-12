package com.kinoticket.backend.rest;

import com.kinoticket.backend.Exceptions.EntityNotFound;
import com.kinoticket.backend.model.FilmShowSeat;
import com.kinoticket.backend.service.FilmShowSeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @PutMapping("/{id}/reserved/{reserved}")
    public ResponseEntity<FilmShowSeat> updateFilmShowSeat(@PathVariable(value = "filmshowId") int filmShowId,
                                                           @PathVariable(value= "id")  int seatId,
                                                           @PathVariable(value = "reserved") boolean reserved) {
        ResponseEntity<FilmShowSeat> responseEntity;
        try {
            FilmShowSeat filmShowSeat = filmShowSeatService.findBySeatAndFilmShow(seatId, filmShowId);
            filmShowSeat = filmShowSeatService.changeSeat(filmShowSeat, reserved);
            responseEntity = new ResponseEntity<>(filmShowSeat, HttpStatus.OK);
        } catch (EntityNotFound enf){
            responseEntity = new ResponseEntity<>((FilmShowSeat) null, HttpStatus.BAD_REQUEST);
        }
            return responseEntity;
    }

}
