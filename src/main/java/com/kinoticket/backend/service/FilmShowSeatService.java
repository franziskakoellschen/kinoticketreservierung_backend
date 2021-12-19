package com.kinoticket.backend.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import com.kinoticket.backend.Exceptions.EntityNotFound;
import com.kinoticket.backend.model.FilmShowSeat;
import com.kinoticket.backend.model.FilmShowSeatStatus;
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

    public FilmShowSeat findBySeatAndFilmShow(long seat_id, long filmshow_id) throws EntityNotFound {
        Optional<FilmShowSeat> filmShowSeat = filmShowSeatRepository.findBySeat_idAndFilmShow_id(seat_id, filmshow_id);
        if (filmShowSeat.isPresent()) {
            return filmShowSeat.get();
        } else {
            throw new EntityNotFound("Can't find Entity");
        }
    }

    public List<List<FilmShowSeat>> getFilmShowSeats(long filmShowId) {

        List<List<FilmShowSeat>> rows = new ArrayList<>();

        List<FilmShowSeat> filmShowSeats = filmShowSeatRepository.findByFilmShow_id(filmShowId);
        for (FilmShowSeat filmShowSeat : filmShowSeats) {
            overdueBlockSetToFreeAgain(filmShowSeat);
        }

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

    public FilmShowSeat changeSeat(FilmShowSeat filmShowSeat, FilmShowSeatStatus status) {
        filmShowSeat.setStatus(status);
        filmShowSeat.setLastChanged(new Date());
        filmShowSeatRepository.save(filmShowSeat);
        return filmShowSeat;
    }

    public boolean canBlock(List<FilmShowSeat> seats, long filmShowId) {
        for (FilmShowSeat fss : seats) {
            FilmShowSeat fssFromRepo = filmShowSeatRepository.findBySeat_idAndFilmShow_id(
                    fss.getSeat().getId(), filmShowId).get();
            if (!canSeatBeBlocked(fssFromRepo)) {
                return false;
            }
        }
        return true;
    }

    private boolean canSeatBeBlocked(FilmShowSeat fs) {
        if (fs.getStatus() == FilmShowSeatStatus.FREE)
            return true;
        if (fs.getStatus() == FilmShowSeatStatus.BOOKED)
            return false;

        return !overdueBlockSetToFreeAgain(fs);
    }

    public boolean overdueBlockSetToFreeAgain(FilmShowSeat seat) {
        if (seat.getStatus() != FilmShowSeatStatus.BLOCKED) {
            return false;
        }

        Date lastChanged = seat.getLastChanged();
        Date fiveMinutesAgo = new Date(System.currentTimeMillis() - TimeUnit.MINUTES.toMillis(5));

        if (lastChanged.before(fiveMinutesAgo)) {
            filmShowSeatRepository.save(changeSeat(seat, FilmShowSeatStatus.FREE));
            return true;
        }

        return false;
    }

    public Iterable<FilmShowSeat> block(List<FilmShowSeat> seats, long filmShowId) {
        if (!canBlock(seats, filmShowId))
            return null;

        ArrayList<FilmShowSeat> blockedSeats = new ArrayList<FilmShowSeat>();

        for (FilmShowSeat fss : seats) {
            FilmShowSeat fssFromRepo = filmShowSeatRepository.findBySeat_idAndFilmShow_id(
                    fss.getSeat().getId(), filmShowId).get();

            blockedSeats.add(changeSeat(fssFromRepo, FilmShowSeatStatus.BLOCKED));
        }

        filmShowSeatRepository.saveAll(blockedSeats);
        return blockedSeats;
    }

    public void book(List<FilmShowSeat> seats, long filmShowId) {
        ArrayList<FilmShowSeat> bookedSeats = new ArrayList<FilmShowSeat>();

        for (FilmShowSeat fss : seats) {
            FilmShowSeat fssFromRepo = filmShowSeatRepository.findBySeat_idAndFilmShow_id(
                    fss.getSeat().getId(), filmShowId).get();
            bookedSeats.add(changeSeat(fssFromRepo, FilmShowSeatStatus.BOOKED));
        }
        filmShowSeatRepository.saveAll(bookedSeats);
    }
}
