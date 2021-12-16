package com.kinoticket.backend.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;

@SpringBootTest
public class EmailServiceTest {

    @Test
    void testSendBookingConfirmation() {
        assertTrue(false);
    }

    @Test
    void testBookingWthMissingTickets() {
        assertTrue(false);
    }

    @Test
    void testBookingWIthInvalidTickets() {
        assertTrue(false);
    }
}
