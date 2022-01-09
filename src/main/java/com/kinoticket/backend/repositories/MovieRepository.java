package com.kinoticket.backend.repositories;

import com.kinoticket.backend.model.FilmShow;
import com.kinoticket.backend.model.Movie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.*;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    @Query("SELECT m FROM Movie m WHERE (:genre IS NULL or upper(m.genre) = :genre) ")
    List<Movie> findMovieWithFilters(@Param("genre") String genre);
}
