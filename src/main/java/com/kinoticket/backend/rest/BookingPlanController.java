package com.kinoticket.backend.rest;

import com.kinoticket.backend.model.BookingPlan;
import com.kinoticket.backend.service.BookingPlanService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/bookingplans")
public class BookingPlanController {

  @Autowired
  BookingPlanService service;

  @GetMapping("/{id}")
  public BookingPlan getBookingPlan(@PathVariable int id) {
    return service.getBookingPlan(id);
  }
}
