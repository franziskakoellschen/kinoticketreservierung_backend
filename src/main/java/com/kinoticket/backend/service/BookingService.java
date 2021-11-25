package com.kinoticket.backend.service;


import com.kinoticket.backend.model.Booking;
import com.kinoticket.backend.repositories.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {

    @Autowired
    private BookingRepository repository;

    public Booking putBooking(Booking booking) {
    return repository.save(booking);
    }

    public Booking getBooking(int id) {
    return repository.findByBookingId(id);
    }

    public Iterable<Booking> getBookings() {
    return repository.findAll();
    }
}
