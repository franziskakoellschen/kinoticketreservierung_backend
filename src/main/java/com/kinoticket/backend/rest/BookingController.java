package com.kinoticket.backend.rest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.kinoticket.backend.Exceptions.EntityNotFound;
import com.kinoticket.backend.Exceptions.MissingParameterException;
import com.kinoticket.backend.dto.BookingDTO;
import com.kinoticket.backend.model.Booking;
import com.kinoticket.backend.service.BookingService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/booking")
public class BookingController {
    Logger logger = LoggerFactory.getLogger(BookingController.class);

    @Autowired
    BookingService service;

    @PostMapping()
    public ResponseEntity<Booking> postBooking(@RequestBody BookingDTO bookingDTO) {

        ResponseEntity<Booking> responseEntity = null;
        Booking sentBooking = null;

        if (!service.areSeatsStillBlocked(bookingDTO)) {
            logger.warn("BookingController: Booking attempt faild: Seats are not blocked anymore.");
            return new ResponseEntity<Booking>(HttpStatus.CONFLICT);
        }

        try {
            sentBooking = service.putBooking(bookingDTO);
            logger.info("BookingController: Booking " + sentBooking.getId() + " successful");
            responseEntity = new ResponseEntity<Booking>(sentBooking, HttpStatus.OK);
        } catch (MissingParameterException mp) {
            logger.error("Invalid Parameter: " + mp.getMessage());
            responseEntity = new ResponseEntity<Booking>(sentBooking, HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Booking> updateBooking(@RequestBody Booking booking) {
        ResponseEntity<Booking> responseEntity = null;
        Booking sentBooking = null;
        try {
            if (booking == null || booking.getId() == null || booking.getTickets() == null) {
                throw new MissingParameterException("You have to pass the full Booking Object, with ID");
            }
            sentBooking = service.updateBooking(booking);
            responseEntity = new ResponseEntity<Booking>(sentBooking, HttpStatus.OK);
        } catch (MissingParameterException mp) {
            log.error("Invalid Parameter: " + mp.getMessage());
            responseEntity = new ResponseEntity<Booking>(booking, HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }

    @GetMapping()
    public Iterable<Booking> getBookings() {
        return service.getBookings();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBooking(@PathVariable long id) {
        ResponseEntity<Booking> responseEntity = null;
        Booking sentBooking = null;
        try {
            sentBooking = service.getBooking(id);
            responseEntity = new ResponseEntity<Booking>(sentBooking, HttpStatus.OK);
        } catch (EntityNotFound mp) {
            log.error("Invalid Parameter: " + mp.getMessage());
            responseEntity = new ResponseEntity<Booking>(sentBooking, HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }

    @GetMapping("/customerId/{id}")
    public ResponseEntity<List<Booking>> getBookingByCustomerId(@PathVariable long id) {
        ResponseEntity<List<Booking>> responseEntity = null;
        List<Booking> sentBookings = null;
        try {
            sentBookings = service.getBookingByCustomerId(id);
            responseEntity = new ResponseEntity<List<Booking>>(sentBookings, HttpStatus.OK);
        } catch (EntityNotFound mp) {
            log.error("Invalid Parameter: " + mp.getMessage());
            responseEntity = new ResponseEntity<List<Booking>>(sentBookings, HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }

    @GetMapping(path = "btw/{firstStringDate}/{secondStringDate}")
    public ResponseEntity<List<Booking>> getBookingBetweenDates(@PathVariable String firstStringDate,
            @PathVariable String secondStringDate) throws ParseException {
        ResponseEntity<List<Booking>> responseEntity = null;
        List<Booking> sentBooking = null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
            Date firstDate = formatter.parse(firstStringDate);
            Date secondDate = formatter.parse(secondStringDate);
            sentBooking = service.getBookingBetweenDates(firstDate, secondDate);
            responseEntity = new ResponseEntity<List<Booking>>(sentBooking, HttpStatus.OK);
        } catch (ParseException e) {
            log.error("Wrong Date format: " + e.getMessage());
            responseEntity = new ResponseEntity<List<Booking>>(sentBooking, HttpStatus.BAD_REQUEST);
        }
        return responseEntity;

    }

    @PostMapping(value = "/cancel/{id}")
    public ResponseEntity<Booking> cancelBooking(@PathVariable long id) {
        ResponseEntity<Booking> responseEntity = null;
        Booking sentBooking = null;
        try {
            sentBooking = service.cancelBooking(id);
            responseEntity = new ResponseEntity<Booking>(sentBooking, HttpStatus.OK);
        } catch (EntityNotFound mp) {
            log.error("Invalid Parameter: " + mp.getMessage());
            responseEntity = new ResponseEntity<Booking>(sentBooking, HttpStatus.BAD_REQUEST);
        }
        return responseEntity;

    }

}
