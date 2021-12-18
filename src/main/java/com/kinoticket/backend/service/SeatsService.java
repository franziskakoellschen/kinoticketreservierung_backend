package com.kinoticket.backend.service;

import com.kinoticket.backend.model.Seat;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SeatsService {

  public ResponseEntity<Seat> changeSeat(int bookingPlanID, int row, int seat, Seat updatedSeat) {
    // TODO --> Only change attribute "reserved"
    // TODO: Create Error message if seat does not exist
    Seat noSeat = null;
    return new ResponseEntity<Seat>(noSeat, HttpStatus.OK);
  }

}
