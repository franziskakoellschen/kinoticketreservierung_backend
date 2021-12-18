package com.kinoticket.backend.rest;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kinoticket.backend.Exceptions.EntityNotFound;
import com.kinoticket.backend.model.FilmShowSeat;
import com.kinoticket.backend.service.FilmShowSeatService;
import com.kinoticket.backend.service.FilmShowService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/filmshows/{filmshowId}/seats")
public class FilmShowSeatController {

    @Autowired
    FilmShowSeatService filmShowSeatService;

    @Autowired
    FilmShowService filmShowService;

    @PutMapping("/{id}/reserved/{reserved}")
    public ResponseEntity<FilmShowSeat> updateFilmShowSeat(@PathVariable(value = "filmshowId") int filmShowId,
            @PathVariable(value = "id") int seatId,
            @PathVariable(value = "reserved") boolean reserved) {
        ResponseEntity<FilmShowSeat> responseEntity;
        try {
            FilmShowSeat filmShowSeat = filmShowSeatService.findBySeatAndFilmShow(seatId, filmShowId);
            filmShowSeat = filmShowSeatService.changeSeat(filmShowSeat, reserved);
            responseEntity = new ResponseEntity<>(filmShowSeat, HttpStatus.OK);
        } catch (EntityNotFound enf) {
            responseEntity = new ResponseEntity<>((FilmShowSeat) null, HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }

    @PostMapping
    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    ResponseEntity<Iterable<FilmShowSeat>> reserveSeats(@RequestBody List<FilmShowSeat> filmShowSeats,
            @PathVariable(value = "filmshowId") long filmShowId) {

        Iterable<FilmShowSeat> reservedFSS = filmShowSeatService.reserve(filmShowSeats, filmShowId);
        if (reservedFSS != null) {
            return new ResponseEntity<Iterable<FilmShowSeat>>(reservedFSS, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
