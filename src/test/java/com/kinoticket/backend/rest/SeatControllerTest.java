package com.kinoticket.backend.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.kinoticket.backend.repositories.SeatsRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
public class SeatControllerTest {

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    SeatsRepository seatsRepository;

    MockMvc mvc;

    @BeforeEach
    void before() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void testAlterSeat() throws Exception {

        this.mvc.perform(put("/seats/bookingplan/1/row/20/seatnumber/13")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{  }"))
                .andExpect(status().isOk())
                .andReturn();
    }
}
