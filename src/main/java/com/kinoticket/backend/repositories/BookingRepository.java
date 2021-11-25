package com.kinoticket.backend.repositories;

import com.kinoticket.backend.model.Booking;
import org.springframework.data.repository.CrudRepository;

public interface BookingRepository extends CrudRepository<Booking, Integer> {
    Booking findByBookingId(int bookingId);
}

