package com.kinoticket.backend.rest;

import com.kinoticket.backend.dto.UserDTO;
import com.kinoticket.backend.model.*;

import com.kinoticket.backend.repositories.AddressRepository;
import com.kinoticket.backend.repositories.BookingRepository;
import com.kinoticket.backend.repositories.UserRepository;
import com.kinoticket.backend.service.EmailService;
import com.kinoticket.backend.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.List;
import java.util.Optional;

@RequestMapping("/user")
@RestController

public class UserController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    EmailService emailService;

    @Autowired
    UserService userService;

    @GetMapping()
    public ResponseEntity<User> getUserInformation() {

        User user = getUserOutOfContext();

        return ResponseEntity.ok().body(user);
    }

    @PostMapping()
    public ResponseEntity<User> setUserInformation(@RequestBody UserDTO newUser) throws com.kinoticket.backend.exceptions.EntityNotFound {

        String username  = getUserOutOfContext().getUsername();
        if (newUser.getUsername().equals(username)) {
            User updatedUser = userService.updateUser(username, newUser);
            return new ResponseEntity<User>(updatedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/bookings")
    public ResponseEntity<List<Booking>> getBookings() {
        User user = getUserOutOfContext();
        List<Booking> bookings = user.getBookings();
        return ResponseEntity.ok().body(bookings);
    }

    
    @PostMapping("/tickets")
    public ResponseEntity<?> getTickets(@RequestParam("bookingId") Long bookingId) throws com.kinoticket.backend.exceptions.MissingParameterException {
        
        Optional<Booking> booking = bookingRepository.findById(bookingId);
        if(booking.isPresent()){
            Booking bookingUnwrapped = booking.get();
            emailService.sendBookingConfirmation(bookingUnwrapped);
            return ResponseEntity.ok().build();
        }
        else{return new ResponseEntity<>(HttpStatus.BAD_REQUEST);}
    }

    @GetMapping("/ticketPdfs")
    public ResponseEntity<List<File>> getTicketPdfs(@RequestParam("bookingId") Long bookingId) throws com.kinoticket.backend.exceptions.MissingParameterException {
        Optional<Booking> booking = bookingRepository.findById(bookingId);

        if(booking.isPresent()){
            Booking bookingUnwrapped = booking.get();
            List<File> files = emailService.generateTicketPdfs(bookingUnwrapped);

            return ResponseEntity.ok().body(files);
        }
        else{return new ResponseEntity<>(HttpStatus.BAD_REQUEST);}




    }



    private User getUserOutOfContext(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return user;
    }
}
