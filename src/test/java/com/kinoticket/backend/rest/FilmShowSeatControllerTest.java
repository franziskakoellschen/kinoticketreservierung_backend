package com.kinoticket.backend.rest;

import com.kinoticket.backend.repositories.FilmShowSeatRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class FilmShowSeatControllerTest {

    @Autowired
    WebApplicationContext webApplicationContext;

    @MockBean
    FilmShowSeatRepository filmShowSeatRepository;

    MockMvc mvc;

    @BeforeEach
    void before() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void testGetSeat() throws Exception {

        this.mvc.perform(get("/filmshows/1/seats/"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void testAlterSeat() throws Exception {

        this.mvc.perform(put("/filmshows/1/seats/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{  }"))
                .andExpect(status().isOk())
                .andReturn();
    }

}
