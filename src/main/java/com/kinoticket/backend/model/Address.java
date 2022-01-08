package com.kinoticket.backend.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "BookingAddress")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(mappedBy = "bookingAddress")
    private Booking booking;

    @OneToOne(mappedBy = "address")
    private User belongingUser;

    @Column
    private String surName;

    @Column
    private String lastName;

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
