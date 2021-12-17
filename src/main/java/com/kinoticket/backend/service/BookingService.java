package com.kinoticket.backend.service;


import com.kinoticket.backend.Exceptions.EntityNotFound;
import com.kinoticket.backend.Exceptions.MissingParameterException;
import com.kinoticket.backend.model.*;
import com.kinoticket.backend.repositories.BookingRepository;
import com.kinoticket.backend.repositories.MovieRepository;
import com.kinoticket.backend.repositories.TicketRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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

    @Autowired
    private FilmShowService filmShowService;

    private List<Ticket> createTickets (List<FilmShowSeat> filmShowSeatList, FilmShow filmShow){
        List<Ticket> ticketList = new ArrayList<>();

        Iterator<FilmShowSeat> filmShowSeatIterator = filmShowSeatList.iterator();
        while(filmShowSeatIterator.hasNext()){
            Ticket ticket = new Ticket();
            FilmShowSeat filmShowSeat = filmShowSeatIterator.next();
            Seat seat = filmShowSeat.getSeat();
            ticket.setMovie(filmShow.getMovie());
            ticket.setFilmShow(filmShow);
            filmShowSeat.setFilmShow(filmShow);
            ticket.setFilmShowSeat(filmShowSeat);
            ticket.setPriceForSeat();
            ticketList.add(ticket);
        }
    return ticketList;
    }


    private Booking createBookingFromDTO (BookingDTO dto){
        Booking booking = new Booking();
        FilmShow filmShow = filmShowService.findById(dto.getFilmShowID());
        booking.setActive(true);
        booking.setBookingAddress(dto.getBookingAddress());
        booking.setPaid(dto.isPaid());
        booking.setTotalSum(dto.getTotalSum());
        booking.setTickets(createTickets(dto.getFilmShowSeatList(), filmShow));
        booking.setMeansOfPayment("Mastercard");
        return booking;
    }


    public Booking putBooking(BookingDTO bookingDTO) throws MissingParameterException{

        Booking booking = createBookingFromDTO(bookingDTO);

       if(booking.getTickets() == null){
           throw new MissingParameterException("Tickets are missing");
       }
       AtomicBoolean missingMovie = new AtomicBoolean(false);
       booking.getTickets().stream().forEach( e -> {
           if(e.getMovie() == null) {
                  missingMovie.set(true);
           }
       });
       if(missingMovie.get()){
           throw new MissingParameterException("Movie is missing");
       }
      try{
       ticketRepository.saveAll(booking.getTickets());}catch (Exception e){
          System.out.println(e.getMessage());
      }
        return bookingRepository.save(booking);
    }

    public Booking getBooking(long id) throws EntityNotFound {
        Booking requestedBooking = bookingRepository.findById(id);
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

