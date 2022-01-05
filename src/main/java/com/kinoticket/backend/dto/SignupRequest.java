package com.kinoticket.backend.dto;

import java.util.Set;

import lombok.Data;

@Data
public class SignupRequest {
    public String username;
    public String password;
    public String email;
    public Set<String> roles;
}