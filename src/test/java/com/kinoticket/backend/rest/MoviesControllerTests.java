package com.kinoticket.backend.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.kinoticket.backend.repositories.MovieRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
public class MoviesControllerTests {
    
    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    MovieRepository movieRepository;

    MockMvc mvc;

    @BeforeEach
    void before() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void testGetAll() throws Exception {

        long oldSize = movieRepository.count();

        this.mvc.perform(get("/movies"))
            .andExpect(status().isOk())
            .andReturn();

        assertEquals(oldSize, movieRepository.count());
    }

    @Test
    void testPost() throws Exception {

        long oldSize = movieRepository.count();

        MvcResult result = this.mvc.perform(
            post("/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    "{ \"title\": \"foo\", \"description\": \"bar\" }"
                )
            )
            .andExpect(status().isOk())
            .andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("title"));
        assertTrue(result.getResponse().getContentAsString().contains("foo"));
        assertTrue(result.getResponse().getContentAsString().contains("description"));
        assertTrue(result.getResponse().getContentAsString().contains("bar"));
        assertEquals(oldSize+1, movieRepository.count());

        movieRepository.delete(
            movieRepository.findById("foo").get()
        );

        assertEquals(oldSize, movieRepository.count());
    }

    @Test
    void testPostNoDuplicates() throws Exception {

        long oldSize = movieRepository.count();

        this.mvc.perform(
            post("/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    "{ \"title\": \"foo\", \"description\": \"bar\" }"
                )
            )
            .andExpect(status().isOk());

        this.mvc.perform(
            post("/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    "{ \"title\": \"foo\", \"description\": \"bar\" }"
                )
            )
            .andExpect(status().isOk());

        assertEquals(oldSize+1, movieRepository.count());

        movieRepository.delete(
            movieRepository.findById("foo").get()
        );

        assertEquals(oldSize, movieRepository.count());
    }
}
