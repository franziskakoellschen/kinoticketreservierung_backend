package com.kinoticket.backend.model;

import java.util.List;

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

    public long getFilmShowID() {
        return filmShowID;
    }

    public BookingAddress getBookingAddress() {
        return bookingAddress;
    }

    public void setBookingAddress(BookingAddress bookingAddress) {
        this.bookingAddress = bookingAddress;
    }

    public void setFilmShowID(long filmShowID) {
        this.filmShowID = filmShowID;
    }

    public List<FilmShowSeat> getFilmShowSeatList() {
        return filmShowSeatList;
    }

    public void setFilmShowSeatList(List<FilmShowSeat> filmShowSeatList) {
        this.filmShowSeatList = filmShowSeatList;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public double getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(double totalSum) {
        this.totalSum = totalSum;
    }
}
