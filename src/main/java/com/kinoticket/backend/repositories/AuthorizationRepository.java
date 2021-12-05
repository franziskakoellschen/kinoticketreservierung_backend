package com.kinoticket.backend.repositories;

import com.kinoticket.backend.model.Authorization;
import org.springframework.data.repository.*;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorizationRepository extends CrudRepository<Authorization, Long> {
    
}
