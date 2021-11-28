package com.kinoticket.backend.service;

import com.kinoticket.backend.model.Seat;
import com.kinoticket.backend.repositories.SeatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SeatsService {

  @Autowired
  private SeatsRepository repository;

  public ResponseEntity changeSeat(int bookingPlanID, int row, int seat, Seat updatedSeat) {
    // TODO --> Only change attribute "reserved"
    return new ResponseEntity(null, HttpStatus.OK);
  }

}
