package com.kinoticket.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {
    @Autowired
    JdbcUserDetailsManager userDetailsService;

    public void register(UserDetails user){
        userDetailsService.createUser(user);
    }

    //User muss bereits eingelogged sein, um sein Passwort zu verändern
    public void changePassword(String oldPassword, String newPassword){
        userDetailsService.changePassword(oldPassword, newPassword);
    }

    public void deleteAccount(String username){
        userDetailsService.deleteUser(username);
    }

    public UserDetails getUserByUsername(String username){
        return userDetailsService.loadUserByUsername(username);
    }

}
