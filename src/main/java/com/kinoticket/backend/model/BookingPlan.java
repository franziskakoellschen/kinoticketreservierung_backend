package com.kinoticket.backend.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "BOOKINGPLANS")
public class BookingPlan implements Serializable {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column
  private int id;

  @Column
  @OneToMany(mappedBy = "bookingplan", cascade = CascadeType.ALL)
  private List<Seat> seats;

  // TODO: SeatPlan
  @OneToOne
  private SeatingPlan seatingPlan;


  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public List<Seat> getSeats() {
    return seats;
  }

  public void setSeats(List<Seat> seats) {
    this.seats = seats;
  }
}
