package com.kinoticket.backend.repositories;

import com.kinoticket.backend.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatsRepository extends JpaRepository<Seat, Integer> {
}
