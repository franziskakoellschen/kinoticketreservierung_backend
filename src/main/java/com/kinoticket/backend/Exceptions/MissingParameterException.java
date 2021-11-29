package com.kinoticket.backend.Exceptions;

public class MissingParameterException extends Exception{
    public MissingParameterException(String errorMessage){
        super(errorMessage);
    }
}
