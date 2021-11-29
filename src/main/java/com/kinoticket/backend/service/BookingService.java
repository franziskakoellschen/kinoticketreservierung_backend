package com.kinoticket.backend.service;


import com.kinoticket.backend.Exceptions.EntityNotFound;
import com.kinoticket.backend.Exceptions.MissingParameterException;
import com.kinoticket.backend.model.Booking;
import com.kinoticket.backend.repositories.BookingRepository;
import com.kinoticket.backend.repositories.MovieRepository;
import com.kinoticket.backend.repositories.TicketRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;


@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private MovieRepository movieRepository;


    public Booking putBooking(Booking booking) throws MissingParameterException{
       if(booking.getTickets() == null){
           throw new MissingParameterException("Tickets are missing");
       }
       AtomicBoolean missingTicket = new AtomicBoolean(false);
       booking.getTickets().stream().forEach( e -> {
           if(e.getMovie() == null) {
                  missingTicket.set(true);
           } else {
               movieRepository.save(e.getMovie());
           }
       });
       if(missingTicket.get()){
           throw new MissingParameterException("Movie is missing");
       }
       ticketRepository.saveAll(booking.getTickets());
        return bookingRepository.save(booking);
    }

    public Booking getBooking(long id) throws EntityNotFound {
        Booking requestedBooking =bookingRepository.findById(id);
        if(requestedBooking == null) {
            throw new EntityNotFound("Can't find Entity by Id" + id);
        }
        return requestedBooking;
    }

    public Iterable<Booking> getBookings() {
    return bookingRepository.findAll();
    }


    public Booking cancelBooking(long id) throws EntityNotFound {
        Booking updatedBooking = bookingRepository.findById(id);
        if(updatedBooking == null) {
            throw new EntityNotFound("Can't find Entity by Id" + id);
        }

       updatedBooking.setActive(false);

       return bookingRepository.save(updatedBooking);

    }

    public Booking updateBooking(Booking booking) {
        booking.setUpdated(new Date());

        return bookingRepository.save(booking);

    }

    public List<Booking> getBookingBetweenDates(Date dateFirst, Date dateAfter) {

        return bookingRepository.findAllByCreatedBetween(dateFirst,dateAfter);
    }

    public List<Booking> getBookingByCustomerId(long id) throws EntityNotFound {
        List<Booking> requestedBooking =bookingRepository.findByCustomerId(id);
        if(requestedBooking == null) {
            throw new EntityNotFound("Can't find Entity by CustomerId" + id);
        }
        return requestedBooking;

    }
}

