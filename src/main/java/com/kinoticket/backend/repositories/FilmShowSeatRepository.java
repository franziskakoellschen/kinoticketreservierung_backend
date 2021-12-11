package com.kinoticket.backend.repositories;

import com.kinoticket.backend.model.FilmShowSeat;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FilmShowSeatRepository extends CrudRepository<FilmShowSeat, Long> {

    List<FilmShowSeat> findByFilmShow_id(int filmshow_id);

    Optional<FilmShowSeat> findBySeat_idAndFilmShow_id(long seat_id, long filmshow_id);
}
