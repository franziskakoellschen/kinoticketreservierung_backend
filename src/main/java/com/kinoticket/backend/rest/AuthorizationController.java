package com.kinoticket.backend.rest;


import com.kinoticket.backend.model.Authorization;

import com.kinoticket.backend.service.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/authorization")
public class AuthorizationController {

    @Autowired
    AuthorizationService service;

    @PostMapping()
    public void registration(@RequestBody Authorization authorization) {
        service.registration(authorization);
    }

    @PatchMapping()
    public void changePassword(@RequestBody Authorization authorization, String newPassword) {
        service.changePassword(authorization, newPassword);
    }

    @PostMapping()
    public void forgotPasswordStart(@RequestBody String email) {
        service.forgotPasswordStart(email);
    }

    @PatchMapping()
    public void forgotPasswordEnd(@RequestBody Authorization authorization, String emailCode) {
        service.forgotPasswordEnd(authorization, emailCode);
    }

    @GetMapping()
    public void logIn(@RequestBody Authorization authorization) {
        service.logIn(authorization);
    }
}
