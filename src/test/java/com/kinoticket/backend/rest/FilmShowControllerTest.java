package com.kinoticket.backend.rest;

import com.kinoticket.backend.repositories.FilmShowRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class FilmShowControllerTest {

    @Autowired
    WebApplicationContext webApplicationContext;

    @MockBean
    FilmShowRepository filmShowRepository;

    MockMvc mvc;

    @BeforeEach
    void before() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void testPostFilmshow() throws Exception {

        this.mvc.perform(post("/filmshows")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{  }"))
                .andExpect(status().isOk())
                .andReturn();
    }
}
