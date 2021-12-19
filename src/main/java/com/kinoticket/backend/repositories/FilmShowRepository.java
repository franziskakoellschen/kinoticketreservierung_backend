package com.kinoticket.backend.repositories;

import com.kinoticket.backend.model.FilmShow;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.*;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public interface FilmShowRepository extends CrudRepository<FilmShow, Long> {

    @Query(value = "SELECT f FROM FilmShow f WHERE date(f.date) > ?1 OR(date(f.date) = ?1 AND f.time >= ?2)")
    List<FilmShow> findFutureFilmShows(Date currentDate, Time currentTime);

    @Query(value = "SELECT f FROM FilmShow f WHERE movie_id = ?1 AND (date(f.date) > ?2 OR(date(f.date) = ?2 AND f.time >= ?3))")
    List<FilmShow> findFutureFilmShowsByMovie(long id, Date currentDate, Time currentTime);
}
