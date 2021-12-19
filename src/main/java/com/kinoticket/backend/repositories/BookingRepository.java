package com.kinoticket.backend.repositories;

import com.kinoticket.backend.model.Booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    Booking findById(long id);

    List<Booking> findAllByCreatedBetween(Date dateFirst, Date dateAfter);

    List<Booking> findByCustomerId(long id);
}

