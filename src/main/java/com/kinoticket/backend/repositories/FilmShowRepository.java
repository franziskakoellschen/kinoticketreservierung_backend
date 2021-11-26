package com.kinoticket.backend.repositories;

import com.kinoticket.backend.model.FilmShow;

import org.springframework.data.repository.*;

public interface FilmShowRepository extends CrudRepository<FilmShow, Long> {
    
}
