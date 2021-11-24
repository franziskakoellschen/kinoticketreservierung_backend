package com.kinoticket.backend.repositories;

import com.kinoticket.backend.model.Movie;

import org.springframework.data.repository.*;

public interface FilmShowRepository extends CrudRepository<Movie, String> {
    
}
