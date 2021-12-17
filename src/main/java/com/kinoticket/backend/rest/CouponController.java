package com.kinoticket.backend.rest;

import com.kinoticket.backend.Exceptions.EntityNotFound;
import com.kinoticket.backend.model.Booking;
import com.kinoticket.backend.model.Coupon;
import com.kinoticket.backend.model.FilmShow;
import com.kinoticket.backend.model.FilmShowDTO;
import com.kinoticket.backend.repositories.CouponRepository;
import com.kinoticket.backend.service.FilmShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/coupons")
public class CouponController {

    @Autowired
    CouponRepository couponRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Coupon> getBooking(@PathVariable long id) {
       Coupon coupon = couponRepository.findById(id);
       if(coupon != null){
           coupon.setActive(false);
           couponRepository.save(coupon);
           return new ResponseEntity<Coupon>(coupon, HttpStatus.OK);
       }

        return new ResponseEntity<Coupon>(coupon, HttpStatus.BAD_REQUEST);

    }


}
