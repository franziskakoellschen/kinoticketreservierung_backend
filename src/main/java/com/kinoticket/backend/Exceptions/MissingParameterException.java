package com.kinoticket.backend.exceptions;

public class MissingParameterException extends Exception{
    public MissingParameterException(String errorMessage){
        super(errorMessage);
    }
}
