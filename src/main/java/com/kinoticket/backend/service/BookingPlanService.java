package com.kinoticket.backend.service;

import com.kinoticket.backend.model.BookingPlan;
import com.kinoticket.backend.repositories.BookingPlanRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingPlanService {

  @Autowired
  BookingPlanRepository repository;

  public BookingPlan getBookingPlan(int id) {
    return repository.findById(id);
  }

}
