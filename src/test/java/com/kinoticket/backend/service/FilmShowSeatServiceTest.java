package com.kinoticket.backend.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import com.kinoticket.backend.UnitTestConfiguration;
import com.kinoticket.backend.model.FilmShow;
import com.kinoticket.backend.model.FilmShowSeat;
import com.kinoticket.backend.model.FilmShowSeatStatus;
import com.kinoticket.backend.model.Seat;
import com.kinoticket.backend.repositories.FilmShowSeatRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(UnitTestConfiguration.class)
public class FilmShowSeatServiceTest {

    @Autowired
    FilmShowSeatService filmShowSeatService;

    @MockBean
    FilmShowSeatRepository filmShowSeatRepository;

    @Test
    void testCanBlock() {
        FilmShow filmShow = new FilmShow();
        filmShow.setId(123);
        List<FilmShowSeat> seats = new ArrayList<FilmShowSeat>();

        FilmShowSeat seatWhichCanBeBlocked = new FilmShowSeat(new Seat(), filmShow);
        seats.add(seatWhichCanBeBlocked);

        when(filmShowSeatRepository.findBySeat_idAndFilmShow_id(anyLong(), anyLong())).thenReturn(Optional.of(seatWhichCanBeBlocked));
        boolean canBlock = filmShowSeatService.canBlock(seats, filmShow.getId());
        assertTrue(canBlock);

        seats.clear();
        FilmShowSeat blockedButOverdueSeat = new FilmShowSeat(new Seat(), filmShow);
        blockedButOverdueSeat.setStatus(FilmShowSeatStatus.BLOCKED);
        blockedButOverdueSeat.setLastChanged(new Date(System.currentTimeMillis() - TimeUnit.MINUTES.toMillis(6)));

        seats.add(blockedButOverdueSeat);

        when(filmShowSeatRepository.findBySeat_idAndFilmShow_id(anyLong(), anyLong())).thenReturn(Optional.of(blockedButOverdueSeat));
        canBlock = filmShowSeatService.canBlock(seats, filmShow.getId());

        assertTrue(canBlock);

        seats.clear();
        FilmShowSeat blockedAndNotOverdueSeat = new FilmShowSeat(new Seat(), filmShow);
        blockedAndNotOverdueSeat.setStatus(FilmShowSeatStatus.BLOCKED);
        blockedAndNotOverdueSeat.setLastChanged(new Date(System.currentTimeMillis() - TimeUnit.MINUTES.toMillis(4)));

        seats.add(blockedAndNotOverdueSeat);

        when(filmShowSeatRepository.findBySeat_idAndFilmShow_id(anyLong(), anyLong())).thenReturn(Optional.of(blockedAndNotOverdueSeat));
        canBlock = filmShowSeatService.canBlock(seats, filmShow.getId());

        assertFalse(canBlock);

        seats.clear();
        FilmShowSeat bookedSeat = new FilmShowSeat(new Seat(), filmShow);
        bookedSeat.setStatus(FilmShowSeatStatus.BOOKED);

        seats.add(bookedSeat);

        when(filmShowSeatRepository.findBySeat_idAndFilmShow_id(anyLong(), anyLong())).thenReturn(Optional.of(bookedSeat));
        canBlock = filmShowSeatService.canBlock(seats, filmShow.getId());

        assertFalse(canBlock);
    }
}
