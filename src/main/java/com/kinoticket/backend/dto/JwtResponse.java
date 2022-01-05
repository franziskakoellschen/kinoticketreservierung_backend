package com.kinoticket.backend.dto;

import java.util.List;

import lombok.Data;

@Data
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private long id;
    private String username;
    private String email;
    private List<String> roles;

    public JwtResponse(
        String token,
        long id,
        String username,
        String email,
        List<String> roles
    ) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }
}
