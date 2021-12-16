package com.kinoticket.backend.Exceptions;

public class FilmShowAlreadyStartedException extends Exception{
    public FilmShowAlreadyStartedException(String errorMessage){
        super(errorMessage);
    }
}
