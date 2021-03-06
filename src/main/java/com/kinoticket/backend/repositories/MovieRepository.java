package com.kinoticket.backend.repositories;

import java.util.List;

import com.kinoticket.backend.model.Movie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    @Query("SELECT m FROM Movie m WHERE (:genre IS NULL or upper(m.genre) = :genre) AND (:searchString IS NULL or upper(m.title) like %:searchString%) ")
    List<Movie> findMovieWithFilters(@Param("genre") String genre, @Param("searchString") String searchString);
}
