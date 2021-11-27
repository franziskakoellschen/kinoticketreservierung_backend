package com.kinoticket.backend.service;


import com.kinoticket.backend.model.Booking;
import com.kinoticket.backend.model.Ticket;
import com.kinoticket.backend.repositories.BookingRepository;
import com.kinoticket.backend.repositories.MovieRepository;
import com.kinoticket.backend.repositories.TicketRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicReference;


@Service
@Slf4j
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private MovieRepository movieRepository;


    public Booking putBooking(Booking booking) {


        booking.getTickets().stream().forEach( e -> {
            movieRepository.save(e.getMovie());
        });
        ticketRepository.saveAll(booking.getTickets());
        return bookingRepository.save(booking);
    }

    public Booking getBooking(long id) {
    return bookingRepository.findById(id);
    }

    public Iterable<Booking> getBookings() {
    return bookingRepository.findAll();
    }


    public Booking cancelBooking(long id) {
       Booking updatedBooking = bookingRepository.findById(id);
       updatedBooking.setActive(false);

       return bookingRepository.save(updatedBooking);

    }
}
