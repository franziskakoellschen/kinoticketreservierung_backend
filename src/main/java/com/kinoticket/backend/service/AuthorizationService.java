package com.kinoticket.backend.service;

import com.kinoticket.backend.model.Authorization;
import com.kinoticket.backend.repositories.AuthorizationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {

    @Autowired
    AuthorizationRepository repository;

    public void registration(Authorization authorization) {
        // TODO

        //Registrierungsdaten abspeichern, Bestätigungsemail mit Link schicken, Code aus Link abspeichern
    }

    public void confirmRegistration(Authorization authorization, String emailCode){
        //TODO

        //Daten und emailCode abgleichen, confirmed auf true setzten, code löschen
    }

    public void changePassword(Authorization authorization, String newPassword) {
        // TODO

        //wenn erfolgreich angemeldet, neues Passwort abspeichern
    }

    public void forgotPasswordStart(String email) {
        // TODO

        //Authorization-Datensatz mit der email finden, 
        //email verschicken mit Link mit Code, um das neue Passwort zu setzten, 
        //Code abspeichern
    }
    
    public void forgotPasswordEnd(Authorization authorization, String emailCode) {
        // TODO

        //email und emailcode abgleichen, im positiv Fall neues Passwort abspeichern
        //emailcode löschen
    }

    public Long logIn(Authorization authorization) {
        return authorization.getId();

        //Log-In Daten abgleichen, im Positiv-Fall Id des Datensatzes zurückgeben
    }
}