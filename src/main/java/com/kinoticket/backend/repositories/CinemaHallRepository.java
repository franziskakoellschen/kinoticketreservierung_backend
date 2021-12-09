package com.kinoticket.backend.repositories;

import com.kinoticket.backend.model.CinemaHall;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CinemaHallRepository extends CrudRepository<CinemaHall, Integer> {
}
