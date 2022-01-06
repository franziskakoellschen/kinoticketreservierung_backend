package com.kinoticket.backend.dto;

import java.util.List;

import com.kinoticket.backend.model.BookingAddress;
import com.kinoticket.backend.model.FilmShowSeat;

import lombok.Data;

@Data
public class BookingDTO {
    private long filmShowID;
    private List<FilmShowSeat> filmShowSeatList;
    private boolean isPaid;
    private double totalSum;
    private BookingAddress bookingAddress;


    public BookingDTO(long filmShowID, List<FilmShowSeat> filmShowSeatList, boolean isPaid, double totalSum, BookingAddress bookingAddress) {
        this.filmShowID = filmShowID;
        this.filmShowSeatList = filmShowSeatList;
        this.isPaid = isPaid;
        this.totalSum = totalSum;
        this.bookingAddress = bookingAddress;
    }
}
