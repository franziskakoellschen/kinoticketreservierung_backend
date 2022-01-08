package com.kinoticket.backend.dto;

import java.util.List;

import com.kinoticket.backend.model.Address;
import com.kinoticket.backend.model.FilmShowSeat;

import lombok.Data;

@Data
public class BookingDTO {
    private long filmShowID;
    private List<FilmShowSeat> filmShowSeatList;
    private boolean isPaid;
    private double totalSum;
    private Address bookingAddress;


    public BookingDTO(long filmShowID, List<FilmShowSeat> filmShowSeatList, boolean isPaid, double totalSum, Address bookingAddress) {
        this.filmShowID = filmShowID;
        this.filmShowSeatList = filmShowSeatList;
        this.isPaid = isPaid;
        this.totalSum = totalSum;
        this.bookingAddress = bookingAddress;
    }
}
