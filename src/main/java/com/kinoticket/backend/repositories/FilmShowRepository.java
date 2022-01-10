package com.kinoticket.backend.repositories;

import com.kinoticket.backend.model.FilmShow;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.*;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public interface FilmShowRepository extends JpaRepository<FilmShow, Long> {

    @Query("SELECT f FROM FilmShow f WHERE date(f.date) > :currentDate OR(date(f.date) = :currentDate AND f.time >= :currentTime)")
    List<FilmShow> findFutureFilmShows(@Param("currentDate") Date currentDate, @Param("currentTime") Time currentTime);

    @Query("SELECT f FROM FilmShow f WHERE movie_id = :id AND (date(f.date) > :currentDate OR(date(f.date) = :currentDate AND f.time >= :currentTime)) ")
    List<FilmShow> findFutureFilmShowsByMovie(@Param("id") long id, @Param("currentDate") Date currentDate, @Param("currentTime") Time currentTime );

    @Query(value = "SELECT f FROM FilmShow f WHERE movie_id = :id AND (date(f.date) > :currentDate OR(date(f.date) = :currentDate AND f.time >= :currentTime))  AND ( :dimension is null OR f.dimension = :dimension ) AND ( :language is null OR f.language = :language )  ")
    List<FilmShow> findFutureFilmShowsByMovieWithFilter(@Param("id") long id, @Param("currentDate") Date currentDate, @Param("currentTime") Time currentTime, @Param("dimension") String dimension, @Param("language") String language);

    @Query(value = "SELECT f FROM FilmShow f WHERE movie_id = :id AND ((date(f.date) > :currentDate  OR date(f.date)  = :currentDate ) AND (date(f.date) < :futureDate OR date(f.date) = :futureDate )) AND ( :dimension is null OR f.dimension = :dimension ) AND ( :language is null OR f.language = :language )")
    List<FilmShow> findFilmShowsWithTwoDates(@Param("id") long id, @Param("currentDate") Date currentDate, @Param("futureDate") Date futureDate, @Param("dimension") String dimension, @Param("language") String language);

    @Query(value = "SELECT f FROM FilmShow f WHERE movie_id = :id AND ((date(f.date) >:fromDate  OR date(f.date)  = :formDate )) AND ( :dimension is null OR f.dimension = :dimension ) AND ( :language is null OR f.language = :language ) ")
    List<FilmShow> findFilmShowsWithFromDate(@Param("id")long id, @Param("fromDate") Date fromDate, @Param("dimension")String dimension, @Param("language") String language);


}
