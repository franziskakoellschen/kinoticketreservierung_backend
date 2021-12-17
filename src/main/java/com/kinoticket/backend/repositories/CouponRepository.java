package com.kinoticket.backend.repositories;

import com.kinoticket.backend.model.Coupon;
import com.kinoticket.backend.model.FilmShowSeat;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CouponRepository extends CrudRepository<Coupon, Long> {

    Coupon findById(long id);

}
