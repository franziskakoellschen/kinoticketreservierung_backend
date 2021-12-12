package com.kinoticket.backend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.kinoticket.backend.Exceptions.EntityNotFound;
import com.kinoticket.backend.model.FilmShowSeat;
import com.kinoticket.backend.repositories.FilmShowSeatRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<List<FilmShowSeat>> getFilmShowSeats(long filmShowId){

        List<List<FilmShowSeat>> rows = new ArrayList<>();

        List<FilmShowSeat> filmShowSeats = filmShowSeatRepository.findByFilmShow_id(filmShowId);

        List<FilmShowSeat> row = new ArrayList<>();
        int currentRow = 1;
        for (FilmShowSeat seat : filmShowSeats) {
            if (seat.getSeat().getRow() != currentRow) {
                currentRow++;
                rows.add(row);
                row = new ArrayList<>();
            }
            row.add(seat);
        }
        rows.add(row);

        return rows;
    }

    public FilmShowSeat changeSeat(FilmShowSeat filmShowSeat, boolean reserved) {
        filmShowSeat.setReserved(reserved);
        filmShowSeatRepository.save(filmShowSeat);
        return filmShowSeat;
    }

    public boolean canReserve(List<FilmShowSeat> seats) {
        for (FilmShowSeat fss : seats) {
            FilmShowSeat fssFromRepo = 
                filmShowSeatRepository.findBySeat_idAndFilmShow_id(
                    fss.getSeat().getId(), fss.getFilmShow().getId()
                ).get();
            if (fssFromRepo.isReserved()) {
                return false;
            }
        }

        return true;
    }

    public boolean reserve(List<FilmShowSeat> seats) {
        for (FilmShowSeat fss : seats) {
            FilmShowSeat fssFromRepo = 
                filmShowSeatRepository.findBySeat_idAndFilmShow_id(
                    fss.getSeat().getId(), fss.getFilmShow().getId()
                ).get();
            
            fssFromRepo.setReserved(true);

            filmShowSeatRepository.save(fssFromRepo);
        }

        return true;
    }
}
