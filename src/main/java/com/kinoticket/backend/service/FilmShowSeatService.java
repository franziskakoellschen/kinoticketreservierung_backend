package com.kinoticket.backend.service;

import com.kinoticket.backend.Exceptions.EntityNotFound;
import com.kinoticket.backend.model.FilmShowSeat;
import com.kinoticket.backend.repositories.FilmShowSeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FilmShowSeatService {

    @Autowired
    FilmShowSeatRepository filmShowSeatRepository;

    public FilmShowSeat findBySeatAndFilmShow(long seat_id, long filmshow_id) throws EntityNotFound{
        Optional<FilmShowSeat> filmShowSeat = filmShowSeatRepository.findBySeat_idAndFilmShow_id(seat_id, filmshow_id);
        if (filmShowSeat.isPresent()) {
            return filmShowSeat.get();
        }
        else {
            throw new EntityNotFound("Can't find Entity");
        }
    }

    public List<FilmShowSeat> getFilmShowSeats(int filmShowId){
        return filmShowSeatRepository.findByFilmShow_id(filmShowId);
    }

    public FilmShowSeat changeSeat(FilmShowSeat filmShowSeat, boolean reserved) {
        filmShowSeat.setReserved(reserved);
        filmShowSeatRepository.save(filmShowSeat);
        return filmShowSeat;
    }
}
