package com.kinoticket.backend.repositories;

import com.kinoticket.backend.model.BookingPlan;

import org.springframework.data.repository.*;

public interface BookingPlanRepository extends CrudRepository<BookingPlan, Integer>{
}
