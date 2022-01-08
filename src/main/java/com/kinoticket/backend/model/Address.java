package com.kinoticket.backend.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String surName;

    @Column
    private String lastName;

    @NotNull
    @Column
    private String emailAddress;

    @Column
    private String street;

    @Column
    private String houseNumber;

    @Column
    private int postCode;

    @Column
    private String city;

    @Column
    private String phoneNumber;

}
