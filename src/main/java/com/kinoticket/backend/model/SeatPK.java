package com.kinoticket.backend.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class SeatPK implements Serializable {

    int row;
    int seatNumber;
    int bookingplan;

    public boolean equals(Object object) {
        if (object instanceof SeatPK) {
            SeatPK pk = (SeatPK) object;
            return row == pk.row && seatNumber == pk.seatNumber && bookingplan == pk.bookingplan;
        } else {
            return false;
        }
    }

    public int hashCode() {
        int hash = 23;
        hash = hash * 31 + row;
        hash = hash * 31 + seatNumber;
        hash = hash * 31 + bookingplan;
        return hash;
    }


}
