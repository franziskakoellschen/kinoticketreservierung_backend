package com.kinoticket.backend.dto;

import javax.validation.constraints.Email;

import lombok.Data;

@Data
public class UserDTO {
    String username;
    @Email String email;
    String surName;
    String lastName;
    String street;
    String houseNumber;
    int postCode;
    String city;
    String phoneNumber;
}
