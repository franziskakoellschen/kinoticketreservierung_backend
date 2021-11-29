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
@Table(name = "BANKDETAILS")
@NoArgsConstructor
@AllArgsConstructor
public class BankDetails {
    @Column
    @NonNull
    @Id
    private int id;

    @Column
    @NonNull
    private String holder;

    @Column
    @NonNull
    private String iban;

}
