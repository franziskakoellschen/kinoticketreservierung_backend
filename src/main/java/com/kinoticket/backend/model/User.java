package com.kinoticket.backend.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;

import org.springframework.lang.NonNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "USERS")
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue
    private long id;

    @NonNull
    private String username;

    @NonNull
    @Email
    private String email;

    @NonNull
    private String password;

    @ManyToMany
    private Set<Role> roles = new HashSet<>();

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
