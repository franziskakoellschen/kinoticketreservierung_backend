package com.kinoticket.backend.email;



public interface EmailSender {
    void send(String to, String email);
}
