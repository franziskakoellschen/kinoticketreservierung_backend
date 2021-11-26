package com.kinoticket.backend.model;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "FILMSHOW")
@Data
public class FilmShow {

    @Column
    @GeneratedValue
    @Id
    private long id;

    @Column
    private Date date;

    @Column
    private Time time;

    @OneToOne
    private SeatingPlan seatingPlan;
}
