package com.kinoticket.backend.repositories;

import java.util.Optional;

import com.kinoticket.backend.model.ERole;
import com.kinoticket.backend.model.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(ERole name);
}