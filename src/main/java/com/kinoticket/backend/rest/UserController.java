package com.kinoticket.backend.rest;

import com.kinoticket.backend.model.*;

import com.kinoticket.backend.repositories.AddressRepository;
import com.kinoticket.backend.repositories.BookingRepository;
import com.kinoticket.backend.repositories.UserRepository;
import com.kinoticket.backend.service.EmailService;
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


    @GetMapping()
    public ResponseEntity<User> getUserInformation() {

        var authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        return ResponseEntity.ok().body(user);


    }

    @PostMapping()
    public ResponseEntity<User> setUserInformation(@RequestBody User user) throws com.kinoticket.backend.exceptions.EntityNotFound {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        User userLoggedIn  = (User) authentication.getPrincipal();
        if(user.getUsername().equals(userLoggedIn.getUsername())){

            addressRepository.save(user.getAddress());
            userRepository.save(user);
            return new ResponseEntity<User>(user, HttpStatus.OK);
        }else{return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);}
    }

    @GetMapping("/bookings")
    public ResponseEntity<List<Booking>> getBookings() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        List<Booking> bookings = user.getBookings();
        return ResponseEntity.ok().body(bookings);

    }

    
    @GetMapping("/tickets")
    public ResponseEntity<List<Ticket>> getTickets(@RequestParam("bookingId") Long bookingId) throws com.kinoticket.backend.exceptions.MissingParameterException {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Optional<Booking> booking = bookingRepository.findById(bookingId);
        if(booking.isPresent()){
            Booking bookingUnwrapped = booking.get();

            if(bookingUnwrapped.getCustomerId() == (user.getId())){
                List<Ticket> tickets = bookingUnwrapped.getTickets();

                return ResponseEntity.ok().body(tickets);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/ticketPdfs")
    public ResponseEntity<List<File>> getTicketPdfs(@RequestParam("bookingId") Long bookingId) throws com.kinoticket.backend.exceptions.MissingParameterException {
        Optional<Booking> booking = bookingRepository.findById(bookingId);
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        if(booking.isPresent()){
            Booking bookingUnwrapped = booking.get();

            if(bookingUnwrapped.getCustomerId() == (user.getId())) {
                List<File> files = emailService.generateTicketPdfs(bookingUnwrapped);

                try {
                    return ResponseEntity.ok().body(files);
                }catch (Exception e){}
                finally {
                    emailService.removeFromDisk(files);
                }
            }

        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }




}
