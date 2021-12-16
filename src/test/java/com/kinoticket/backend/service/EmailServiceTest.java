package com.kinoticket.backend.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.internet.MimeMessage;

import com.kinoticket.backend.UnitTestConfiguration;
import com.kinoticket.backend.model.Booking;
import com.kinoticket.backend.model.CinemaHall;
import com.kinoticket.backend.model.FilmShow;
import com.kinoticket.backend.model.FilmShowSeat;
import com.kinoticket.backend.model.Movie;
import com.kinoticket.backend.model.Seat;
import com.kinoticket.backend.model.Ticket;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.mail.javamail.JavaMailSender;

@SpringBootTest
@Import(UnitTestConfiguration.class)
public class EmailServiceTest {

    @Autowired
    EmailService emailService;

    @MockBean
    JavaMailSender emailSender;

    @Test
    void testSendBookingConfirmation() {

        MimeMessage mockMimeMessage = Mockito.mock(MimeMessage.class);

        Mockito.doNothing()
            .when(emailSender)
            .send(Mockito.any(MimeMessage.class));
        Mockito.when(emailSender.createMimeMessage()).thenReturn(mockMimeMessage);
        assertTrue(
            emailService.sendBookingConfirmation(
                createExampleBooking()
            )
        );
    }

    @Test
    void testBookingWthMissingTickets() {
        Booking booking = createExampleBooking();
        booking.setTickets(null);

        assertFalse(
            emailService.sendBookingConfirmation(booking)
        );
    }

    @Test
    void testBookingWIthInvalidTickets() {
        Booking booking = createExampleBooking();
        booking.getTickets().add(new Ticket());

        assertFalse(
            emailService.sendBookingConfirmation(booking)
        );
    }

    
    private Booking createExampleBooking() {
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


        List<Ticket> tickets = new ArrayList<>();
        tickets.add(t1);

        Booking b = new Booking();
        b.setId(9087L);
        b.setTickets(tickets);
        String email = "dummy";
        b.setEmail(email);

        return b;
    }



}
