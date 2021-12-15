package com.kinoticket.backend.model;

import java.util.Date;
import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "FILMSHOW")
@NoArgsConstructor
@AllArgsConstructor
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

    public FilmShow(Date date, Time time, SeatingPlan seatingPlan) {
        this.date = date;
        this.time = time;
        this.seatingPlan = seatingPlan;
    }
}
