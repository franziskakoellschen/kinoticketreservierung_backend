package com.kinoticket.backend.rest;

import com.kinoticket.backend.model.Seat;
import com.kinoticket.backend.service.SeatsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/seats")
public class SeatsController {

  @Autowired
  SeatsService service;

  @PutMapping("/bookingplan/{id}/row/{row}/seatnumber/{seat}")
  public ResponseEntity<Seat> changeSeat(@PathVariable(value = "id") int id,
                                   @PathVariable(value = "row") int row,
                                   @PathVariable(value = "seat") int seat,
                                   @RequestBody Seat updatedSeat) {
    return service.changeSeat(id, row, seat, updatedSeat);
  }
}
