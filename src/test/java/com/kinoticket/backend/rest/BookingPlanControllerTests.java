package com.kinoticket.backend.rest;

import com.kinoticket.backend.repositories.BookingPlanRepository;
import com.kinoticket.backend.repositories.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class BookingPlanControllerTests {

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    BookingPlanRepository bookingPlanRepository;

    MockMvc mvc;

    @BeforeEach
    void before() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void testGetById() throws Exception {

        // TODO: Bookingplan(1) does not exist yet --> create this booking test and check if attribute values are correct
        // TODO: Mock BookingPlanService (to act as a Unit Test)

        this.mvc.perform(get("/bookingplans/1"))
                .andExpect(status().isOk())
                .andReturn();

    }

}
