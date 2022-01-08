package com.kinoticket.backend.rest;

import com.kinoticket.backend.dto.UserDTO;
import com.kinoticket.backend.exceptions.EntityNotFound;
import com.kinoticket.backend.exceptions.MissingParameterException;
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

        var authentication = SecurityContextHolder.getContext().getAuthentication();
        User requestedUser = (User) authentication.getPrincipal();

        Optional<User> user = userRepository.findById(requestedUser.getId());

        if(user.isPresent()){
            return ResponseEntity.ok().body(user.get());
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    }

    @PostMapping()
    public ResponseEntity<User> setUserInformation(@RequestBody UserDTO newUser) throws EntityNotFound {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        User requestedUser  = (User) authentication.getPrincipal();

        if (newUser.getUsername().equals(requestedUser.getUsername())) {
            User updatedUser = userService.updateUser(requestedUser.getUsername(), newUser);
            return new ResponseEntity<User>(updatedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/bookings")
    public ResponseEntity<List<Booking>> getBookings() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        User requestedUser = (User) authentication.getPrincipal();

        Optional<User> user = userRepository.findByUsername(requestedUser.getUsername());

        if(user.isPresent()){
            List<Booking> bookings = user.get().getBookings();
            return ResponseEntity.ok().body(bookings);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/tickets")
    public ResponseEntity<?> sendBookingConfirmationAgain(@RequestParam("bookingId") long bookingId) throws MissingParameterException {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        User requestedUser = (User) authentication.getPrincipal();
        Optional<User> user = userRepository.findByUsername(requestedUser.getUsername());

        if (user.isPresent()){
            for (Booking b : user.get().getBookings()) {
                if(b.getId() == bookingId) {
                    emailService.sendBookingConfirmation(b);
                    return ResponseEntity.ok().build();
                }
            }
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
