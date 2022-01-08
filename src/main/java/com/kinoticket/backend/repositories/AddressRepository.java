package com.kinoticket.backend.repositories;

import com.kinoticket.backend.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    Address findById(long id);
}

