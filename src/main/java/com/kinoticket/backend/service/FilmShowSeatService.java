package com.kinoticket.backend.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import com.kinoticket.backend.Exceptions.EntityNotFound;
import com.kinoticket.backend.model.FilmShowSeat;
import com.kinoticket.backend.repositories.FilmShowSeatRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FilmShowSeatService {

    private class FilmShowSeatComparator implements Comparator<FilmShowSeat> {

        @Override
        public int compare(FilmShowSeat o1, FilmShowSeat o2) {
            if (o1.getSeat().getRow() != o2.getSeat().getRow()) {
                return o1.getSeat().getRow() - o2.getSeat().getRow();
            }
            return o1.getSeat().getSeatNumber() - o2.getSeat().getSeatNumber();
        }
    }

    @Autowired
    FilmShowSeatRepository filmShowSeatRepository;

    @Autowired
    EmailService emailService;

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

        filmShowSeats.sort(new FilmShowSeatComparator());

        // split in rows
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

    public boolean canReserve(List<FilmShowSeat> seats, long filmShowId) {
        for (FilmShowSeat fss : seats) {
            FilmShowSeat fssFromRepo = 
                filmShowSeatRepository.findBySeat_idAndFilmShow_id(
                    fss.getSeat().getId(), filmShowId
                ).get();
            if (fssFromRepo.isReserved()) {
                return false;
            }
        }
        return true;
    }

    public Iterable<FilmShowSeat> reserve(List<FilmShowSeat> seats, long filmShowId) {
        if (!canReserve(seats, filmShowId)) return null;

        ArrayList<FilmShowSeat> reservedSeats = new ArrayList<FilmShowSeat>();

        for (FilmShowSeat fss : seats) {
            FilmShowSeat fssFromRepo =
                filmShowSeatRepository.findBySeat_idAndFilmShow_id(
                    fss.getSeat().getId(), filmShowId
                ).get();

            fssFromRepo.setReserved(true);
            reservedSeats.add(fssFromRepo);
        }

        filmShowSeatRepository.saveAll(reservedSeats);
        return reservedSeats;
    }
}
