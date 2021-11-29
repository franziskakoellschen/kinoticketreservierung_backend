package com.kinoticket.backend.repositories;

import com.kinoticket.backend.model.BankDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankDetailRepository extends JpaRepository<BankDetails, Long> {
    BankDetails findById(long id);
}
