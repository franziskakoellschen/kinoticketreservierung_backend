package com.kinoticket.backend.rest.request;

import lombok.Data;

@Data
public class PasswordChangeRequest {
    String token;
    String newPassword;
}
