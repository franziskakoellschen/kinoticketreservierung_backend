package com.kinoticket.backend.rest;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kinoticket.backend.UnitTestConfiguration;
import com.kinoticket.backend.model.Booking;
import com.kinoticket.backend.model.Ticket;
import com.kinoticket.backend.model.User;
import com.kinoticket.backend.repositories.UserRepository;
import com.kinoticket.backend.service.EmailService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.NestedServletException;

@SpringBootTest
@Import(UnitTestConfiguration.class)
public class UserControllerTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private EmailService emailService;

    private MockMvc mockMvc;

    @BeforeEach
    void before() {
        JacksonTester.initFields(this, new ObjectMapper());
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @WithAnonymousUser
    public void testAnonymousUserNoAccess() throws Exception {

        assertThrows(NestedServletException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                mockMvc.perform(get("/user"));
            }
        });
    }

    @Test
    @WithMockUser
    public void testUserHasAccess() throws Exception {
        mockMvc.perform(get("/user")).andExpect(status().isBadRequest());

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(new User()));
        mockMvc.perform(get("/user")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void testUserBookings() throws Exception {
        User user = new User();
        Booking b = new Booking();
        b.setId(1L);
        ArrayList<Booking> bookings = new ArrayList<>();
        bookings.add(b);
        user.setBookings(bookings);

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        mockMvc.perform(get("/user/bookings")).andExpect(status().isUnauthorized());

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        mockMvc.perform(get("/user/bookings")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void testUserTickets() throws Exception {
        User user = new User();
        Booking b = new Booking();
        Ticket t = new Ticket();
        ArrayList<Ticket> tickets = new ArrayList<>();
        tickets.add(t);
        b.setId(1L);
        b.setTickets(tickets);
        ArrayList<Booking> bookings = new ArrayList<>();
        bookings.add(b);
        user.setBookings(bookings);

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        mockMvc.perform(get("/user/tickets?bookingId=1")).andExpect(status().isBadRequest());

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        mockMvc.perform(get("/user/tickets?bookingId=2")).andExpect(status().isBadRequest());

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        Mockito.doNothing().when(emailService).sendBookingConfirmation(any());
        mockMvc.perform(get("/user/tickets?bookingId=1")).andExpect(status().isOk());
    }
}
