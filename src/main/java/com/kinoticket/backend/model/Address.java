package com.kinoticket.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "ADDRESSES")
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Column
    @NonNull
    @Id
    private int id;

    @Column
    @NonNull
    private String street;

    @Column
    @NonNull
    private int streetNumber;

    @Column
    @NonNull
    private char addition;

    @Column
    @NonNull
    private int postalCode;

    @Column
    @NonNull
    private String town;
}
