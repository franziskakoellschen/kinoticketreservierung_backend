package com.kinoticket.backend.dto;

import lombok.Data;

@Data
public class LoginRequest {
    public String username;
    public String password;
}