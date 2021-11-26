package com.kinoticket.backend.rest;


import com.kinoticket.backend.model.Booking;

import com.kinoticket.backend.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    BookingService service;

    @PostMapping()
    public Booking postBooking(@RequestBody Booking booking) {
        return service.putBooking(booking);
    }

    @GetMapping()
    public Iterable<Booking> getBookings() {
        return service.getBookings();
    }

    @GetMapping("/{id}")
    public Booking getBooking(@PathVariable long id) {
        return service.getBooking(id);
    }

    @PutMapping(value="/cancel")
    public Booking updateBooking(@RequestBody long id){
        return service.cancelBooking(id);
    }
}
