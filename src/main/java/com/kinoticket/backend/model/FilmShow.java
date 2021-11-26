package com.kinoticket.backend.model;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "FILMSHOW")
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
    
    /* Getters and Setters */

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public SeatingPlan getSeatingPlan() {
        return seatingPlan;
    }

    public void setSeatingPlan(SeatingPlan seatingPlan) {
        this.seatingPlan = seatingPlan;
    }
}
