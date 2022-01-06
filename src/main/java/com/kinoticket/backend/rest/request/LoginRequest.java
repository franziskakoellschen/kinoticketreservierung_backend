package com.kinoticket.backend.rest.request;

import lombok.Data;

@Data
public class LoginRequest {
    public String username;
    public String password;
}