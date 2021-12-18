package com.kinoticket.backend.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import com.kinoticket.backend.Exceptions.EntityNotFound;
import com.kinoticket.backend.Exceptions.MissingParameterException;
import com.kinoticket.backend.model.Booking;
import com.kinoticket.backend.model.BookingDTO;
import com.kinoticket.backend.model.FilmShowSeat;
import com.kinoticket.backend.model.Ticket;
import com.kinoticket.backend.repositories.BookingRepository;
import com.kinoticket.backend.repositories.FilmShowRepository;
import com.kinoticket.backend.repositories.TicketRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private FilmShowRepository filmShowRepository;

    private List<Ticket> createTickets(List<FilmShowSeat> filmShowSeatList) {
        List<Ticket> ticketList = new ArrayList<>();

        for (FilmShowSeat filmShowSeat : filmShowSeatList) {
            Ticket ticket = new Ticket();
            ticket.setMovie(filmShowSeat.getFilmShow().getMovie());
            ticket.setFilmShow(filmShowSeat.getFilmShow());
            ticket.setFilmShowSeat(filmShowSeat);
            ticket.setPriceForSeat();
            ticketList.add(ticket);
        }
        return ticketList;
    }

    private Booking createBookingFromDTO(BookingDTO dto) {
        Booking booking = new Booking();
        booking.setActive(true);
        booking.setBookingAddress(dto.getBookingAddress());
        booking.setPaid(dto.isPaid());
        booking.setTotalSum(dto.getTotalSum());
        for (FilmShowSeat filmShowSeat : dto.getFilmShowSeatList()) {
            filmShowSeat.setFilmShow(
                    filmShowRepository.findById(dto.getFilmShowID()).get());
        }
        booking.setTickets(createTickets(dto.getFilmShowSeatList()));
        booking.setMeansOfPayment("Mastercard");
        return booking;
    }

    public Booking putBooking(BookingDTO bookingDTO) throws MissingParameterException {

        if (bookingDTO.getFilmShowSeatList() == null || bookingDTO.getFilmShowSeatList().isEmpty()) {
            throw new MissingParameterException("No FilmShowSeats provided");
        }

        Booking booking = createBookingFromDTO(bookingDTO);

        if (booking.getTickets() == null || booking.getTickets().isEmpty()) {
            throw new MissingParameterException("Tickets are missing");
        }
        AtomicBoolean missingMovie = new AtomicBoolean(false);
        booking.getTickets().stream().forEach(e -> {
            if (e.getMovie() == null) {
                missingMovie.set(true);
            }
        });
        if (missingMovie.get()) {
            throw new MissingParameterException("Movie is missing");
        }

        ticketRepository.saveAll(booking.getTickets());
        Booking persistedBooking = bookingRepository.save(booking);

        emailService.sendBookingConfirmation(persistedBooking);

        return persistedBooking;
    }

    public Booking getBooking(long id) throws EntityNotFound {
        Booking requestedBooking = bookingRepository.findById(id);
        if (requestedBooking == null) {
            throw new EntityNotFound("Can't find Entity by Id" + id);
        }
        return requestedBooking;
    }

    public Iterable<Booking> getBookings() {
        return bookingRepository.findAll();
    }

    public Booking cancelBooking(long id) throws EntityNotFound {
        Booking updatedBooking = bookingRepository.findById(id);
        if (updatedBooking == null) {
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

        return bookingRepository.findAllByCreatedBetween(dateFirst, dateAfter);
    }

    public List<Booking> getBookingByCustomerId(long id) throws EntityNotFound {
        List<Booking> requestedBooking = bookingRepository.findByCustomerId(id);
        if (requestedBooking == null) {
            throw new EntityNotFound("Can't find Entity by CustomerId" + id);
        }
        return requestedBooking;

    }
}
