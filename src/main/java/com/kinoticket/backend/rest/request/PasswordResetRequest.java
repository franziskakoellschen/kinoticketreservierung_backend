package com.kinoticket.backend.rest.request;

import lombok.Data;

@Data
public class PasswordResetRequest {
    String email;
}
