package com.kinoticket.backend.model;

import org.springframework.lang.NonNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Booking")
public class Booking {

    @Column
    @NonNull
    @Id
    private int id;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() { return  this.id; }

//to be finished in another ticket
}
