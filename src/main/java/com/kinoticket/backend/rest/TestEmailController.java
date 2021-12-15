package com.kinoticket.backend.rest;

import com.kinoticket.backend.service.EmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
public class TestEmailController {

    @Autowired
    EmailService emailService;

    @PostMapping()
    public void sendEmail(@RequestBody String email) {
        String mail = email.replace("%40", "@");
        emailService.sendEmail(mail, "This is a subject!", "This is a Test Message");
    }
}

