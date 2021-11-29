package com.kinoticket.backend.model;

import javax.persistence.*;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "USERS")
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;

    @Column
    @NotNull
    private String email;

    @Column
    @NotNull
    private String name;

    @Column
    @NotNull
    private String vorname;

    @OneToOne
    private Address address;

    @OneToOne
    private BankDetails bankDetails;


}
