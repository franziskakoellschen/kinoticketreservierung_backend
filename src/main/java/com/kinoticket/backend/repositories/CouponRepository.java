package com.kinoticket.backend.repositories;

import com.kinoticket.backend.model.Coupon;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponRepository extends CrudRepository<Coupon, Long> {
}
