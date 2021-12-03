package com.kinoticket.backend.repositories;

import com.kinoticket.backend.model.BookingPlan;

import org.springframework.data.repository.*;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingPlanRepository extends CrudRepository<BookingPlan, Integer>{
}
