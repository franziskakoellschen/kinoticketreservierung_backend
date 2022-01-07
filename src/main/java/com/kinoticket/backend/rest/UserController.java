package com.kinoticket.backend.rest;

import com.kinoticket.backend.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/user")
@RestController

public class UserController {
    
    @GetMapping()
    public ResponseEntity<User> getUserInformation(@RequestParam("token") String token) {

        VerificationToken verificationToken = verTokenrepository.findByToken(token);
        


    }

    @PostMapping()
    public ResponseEntity<User> setUserInformation(@RequestParam("token") String token, @RequestBody User user) {

        // token verifikation
        VerificationToken verificationToken = verTokenrepository.findByToken(token);
        


    }

    @GetMapping("/bookings")
    public ResponseEntity<List<Booking>> getBookings(@RequestParam("token") String token) {

    }

    
    @GetMapping("/tickets")
    public ResponseEntity<List<Booking>> getBookings(@RequestParam("bookingId") String bookingId) {
        // bookingrepository.getById

        emailService.createTicketPdf(booking)
        removeFromDisk(List<File> files)

    }return 
}
