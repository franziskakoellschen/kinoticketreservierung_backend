package com.kinoticket.backend.repositories;

import com.kinoticket.backend.model.Booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    Booking findById(long id);
}

