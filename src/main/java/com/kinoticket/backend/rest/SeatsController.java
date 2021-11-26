package com.kinoticket.backend.rest;

import com.kinoticket.backend.model.BookingPlan;
import com.kinoticket.backend.service.SeatsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seats")
public class SeatsController {

  @Autowired
  SeatsService service;

  @PutMapping("/bookingplan/{id}/row/{row}/seat/{seat}/reserved/{reserved}")
  public ResponseEntity changeSeat(@PathVariable(value = "id") int id,
                                   @PathVariable(value = "row") int row,
                                   @PathVariable(value = "seat") int seat,
                                   @PathVariable(value = "reserved") boolean reserved) {
    return service.changeSeat(id, row, seat, reserved);
  }
}
