package com.kinoticket.backend.rest;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.kinoticket.backend.model.Booking;
import com.kinoticket.backend.model.CinemaHall;
import com.kinoticket.backend.model.FilmShow;
import com.kinoticket.backend.model.FilmShowSeat;
import com.kinoticket.backend.model.Movie;
import com.kinoticket.backend.model.Seat;
import com.kinoticket.backend.model.Ticket;
import com.kinoticket.backend.service.EmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    EmailService emailService;

    public TestController() {}

    @GetMapping("/testRequest")
    public String testRequest() {
        return "Hello from Backendiius!";
    }

    @PostMapping("/email")
    public HttpStatus sendEmail(@RequestBody String email) {
        

        if(
            emailService.sendBookingConfirmation(
                createExampleBooking(email)
            )
        ) {
            return HttpStatus.OK;
        }
        return HttpStatus.BAD_REQUEST;
    }


    private Booking createExampleBooking(String email) {
        Ticket t1 = new Ticket();

        t1.setId(1234);
        FilmShow fs = new FilmShow();
        Movie m = new Movie();
        CinemaHall ch = new CinemaHall();
        ch.setId(3);
        m.setTitle("Harry Potter");
        fs.setMovie(m);
        fs.setDate(new Date());
        fs.setTime(new Time(12, 30, 0));
        t1.setFilmShow(fs);

        FilmShowSeat fss = new FilmShowSeat();
        Seat seat = new Seat();
        seat.setCinemaHall(ch);
        seat.setPriceCategory(2);
        seat.setRow(2);
        seat.setSeatNumber(3);
        fss.setSeat(seat);
        fs.setCinemaHall(ch);
        t1.setFilmShowSeat(fss);
        t1.setPrice(9);

        Ticket t2 = new Ticket();

        List<Ticket> tickets = new ArrayList<>();
        tickets.add(t1);
        // tickets.add(t2);

        Booking b = new Booking();
        b.setId(9087L);
        b.setTickets(tickets);

        b.setEmail(email.replace("%40", "@"));

        return b;
    }

}
