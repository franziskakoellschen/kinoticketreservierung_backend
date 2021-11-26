package com.kinoticket.backend.service;

import com.kinoticket.backend.model.BookingPlan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SeatsService {

  /*
  TODO: Autowire with seats repository
   */

  public ResponseEntity changeSeat(int bookingPlanID, int row, int seat, boolean reserved) {
    // TODO
    return new ResponseEntity(null, HttpStatus.OK);
  }

}
