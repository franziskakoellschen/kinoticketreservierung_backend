package com.kinoticket.backend.repositories;

import com.kinoticket.backend.model.FilmShowSeat;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilmShowSeatRepository extends CrudRepository<FilmShowSeat, Integer> {

    List<FilmShowSeat> findByFilmShow_id(int filmshow_id);
}
