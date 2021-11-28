package com.kinoticket.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "SEATS")
@IdClass(SeatPK.class)
@Data
public class Seat {

    @Id
    private int row;

    @Id
    private int seatNumber;

    @Id
    @ManyToOne(targetEntity = BookingPlan.class)
    @JoinColumn(name = "bookingplan_id", referencedColumnName = "id")
    @JsonIgnore
    private BookingPlan bookingplan;

    @Column
    @NotNull
    private boolean reserved;

    @Column
    @NotNull
    private int priceCategory;

    public Seat(int row, int seatNumber, BookingPlan bookingplan, boolean reserved, int priceCategory) {
        this.row = row;
        this.seatNumber = seatNumber;
        this.bookingplan = bookingplan;
        this.reserved  = reserved;
        this.priceCategory = priceCategory;
    }

    public int getRow() {
        return row;
    }


    public int getSeatNumber() {
        return seatNumber;
    }

    public BookingPlan getBookingplan() {
        return bookingplan;
    }

    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }

    public int getPriceCategory() {
        return priceCategory;
    }

    public void setPriceCategory(int priceCategory) {
        this.priceCategory = priceCategory;
    }
}
