package com.kinoticket.backend.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class SeatingPlan {

    @GeneratedValue
    @Id
    private int id;

}
