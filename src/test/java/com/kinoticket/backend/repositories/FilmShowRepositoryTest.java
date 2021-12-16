package com.kinoticket.backend.repositories;

import com.kinoticket.backend.model.FilmShow;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FilmShowRepositoryTest {

    @Autowired
    FilmShowRepository filmShowRepository;

    @Test
    void getFilmShows(){
        LocalDateTime dateTime = LocalDateTime.now(ZoneId.of("CET"));
        java.sql.Date sqlDate = java.sql.Date.valueOf(dateTime.toLocalDate());
        java.sql.Time sqlTime = java.sql.Time.valueOf(dateTime.toLocalTime());
        List<FilmShow> filmShows = filmShowRepository.findFutureFilmShows(sqlDate, sqlTime);
        for (FilmShow f : filmShows) {
            java.sql.Date dateSql = new java.sql.Date(f.getDate().getTime());
            assertTrue( sqlDate.before(dateSql) || (sqlDate.equals(dateSql) && sqlTime.before(f.getTime())));
        }
    }

    @Test
    void getFilmShowsByMovie(){
        LocalDateTime dateTime = LocalDateTime.now(ZoneId.of("CET"));
        java.sql.Date sqlDate = java.sql.Date.valueOf(dateTime.toLocalDate());
        java.sql.Time sqlTime = java.sql.Time.valueOf(dateTime.toLocalTime());
        List<FilmShow> filmShows = filmShowRepository.findFutureFilmShowsByMovie(2000, sqlDate, sqlTime);
        for (FilmShow f : filmShows) {
            java.sql.Date dateSql = new java.sql.Date(f.getDate().getTime());
            assertTrue( sqlDate.before(dateSql) || (sqlDate.equals(dateSql) && sqlTime.before(f.getTime())));
        }
    }

}