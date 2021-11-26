package com.kinoticket.backend.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.lang.NonNull;

@Entity
@Table(name = "BOOKINGPLANS")
public class BookingPlan implements Serializable {

  @Column
  @NonNull
  @Id
  private int id;

  // TODO


  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }
}
