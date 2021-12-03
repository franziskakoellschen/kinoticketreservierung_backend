package com.kinoticket.backend.service;

import com.kinoticket.backend.model.BookingPlan;
import com.kinoticket.backend.repositories.BookingPlanRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookingPlanService {

  @Autowired
  private BookingPlanRepository repository;

  public Optional<BookingPlan> getBookingPlan(int id) {
    return repository.findById(id);
    // TODO: Handle if booking plan not found
  }

}
