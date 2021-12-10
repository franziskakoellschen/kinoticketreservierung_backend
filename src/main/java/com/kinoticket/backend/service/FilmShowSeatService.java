package com.kinoticket.backend.service;

import com.kinoticket.backend.model.FilmShowSeat;
import com.kinoticket.backend.repositories.FilmShowSeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilmShowSeatService {

    @Autowired
    FilmShowSeatRepository filmShowSeatRepository;

    public List<FilmShowSeat> getFilmShowSeats(int filmShowId){
        return filmShowSeatRepository.findByFilmShow_id(filmShowId);
    }

    public ResponseEntity<FilmShowSeat> changeSeat(FilmShowSeat updatedFilmShowSeat) {
        // TODO --> Only change attribute "reserved"
        // TODO: Create Error message if seat does not exist
        FilmShowSeat noSeat = null;
        return new ResponseEntity<FilmShowSeat>(noSeat, HttpStatus.OK);
    }
}
