package com.kinoticket.backend.rest;

import com.kinoticket.backend.model.Coupon;
import com.kinoticket.backend.repositories.CouponRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/coupons")
public class CouponController {

    @Autowired
    CouponRepository couponRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Coupon> getBooking(@PathVariable long id) {
        Coupon coupon = couponRepository.findById(id);
        if (coupon != null) {
            coupon.setActive(false);
            couponRepository.save(coupon);
            return new ResponseEntity<Coupon>(coupon, HttpStatus.OK);
        }

        return new ResponseEntity<Coupon>(coupon, HttpStatus.BAD_REQUEST);

    }

}
