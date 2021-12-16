package com.kinoticket.backend.rest;

import java.util.ArrayList;
import java.util.List;

import com.kinoticket.backend.model.Booking;
import com.kinoticket.backend.model.Ticket;
import com.kinoticket.backend.service.EmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    EmailService emailService;

    public TestController() {}

    @GetMapping("/testRequest")
    public String testRequest() {
        return "Hello from Backendiius!";
    }

    @PostMapping("/email")
    public ResponseEntity sendEmail(@RequestBody String email) {

        Ticket t1 = new Ticket();
        Ticket t2 = new Ticket();

        List<Ticket> tickets = new ArrayList<>();
        tickets.add(t1);
        tickets.add(t2);

        Booking b = new Booking();
        b.setTickets(tickets);

        b.setEmail(email.replace("%40", "@"));

        if(
            emailService.sendBookingConfirmation(b)
        ) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
