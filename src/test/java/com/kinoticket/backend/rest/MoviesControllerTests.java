package com.kinoticket.backend.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import com.kinoticket.backend.repositories.MovieRepository;

import org.junit.jupiter.api.AfterEach;
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
        if (movieRepository.findById("foo").isPresent()) {
            movieRepository.delete(movieRepository.findById("foo").get());
        }
    }

    @AfterEach
    void after() {
        if (movieRepository.findById("foo").isPresent()) {
            movieRepository.delete(movieRepository.findById("foo").get());
        }
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
                        .content("{\"title\": \"foo\",\"year\":2000,\"shortDescription\":\"bar\",\"fsk\":16}"))
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("title"));
        assertTrue(result.getResponse().getContentAsString().contains("foo"));
        assertTrue(result.getResponse().getContentAsString().contains("year"));
        assertTrue(result.getResponse().getContentAsString().contains("2000"));
        assertTrue(result.getResponse().getContentAsString().contains("shortDescription"));
        assertTrue(result.getResponse().getContentAsString().contains("bar"));
        assertTrue(result.getResponse().getContentAsString().contains("fsk"));
        assertTrue(result.getResponse().getContentAsString().contains("16"));

        assertEquals(oldSize+1, movieRepository.count());

        movieRepository.delete(movieRepository.findById("foo").get());

        assertEquals(oldSize, movieRepository.count());
    }

    @Test
    void testPostWithFilmShow() throws Exception {
        this.mvc.perform(
            post("/movies")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"title\": \"foo\",\"filmShows\":[{\"date\":\"2021-09-20\"}]}"))
            .andExpect(status().isOk());
    }

    @Test
    void testGetNonPresentId() throws Exception {
        this.mvc.perform(
            get("/movies/4711"))
            .andExpect(status().isOk())
            .andExpect(content().string(""));
    }

    @Test
    void testGetFilmShowForNonPresentId() throws Exception {
        this.mvc.perform(
            get("/movies/4711/filmShows"))
            .andExpect(status().isOk())
            .andExpect(content().string(""));
    }

    @Test
    void testPostNoDuplicates() throws Exception {

        long oldSize = movieRepository.count();

        this.mvc.perform(
                post("/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"foo\",\"year\":2000,\"shortDescription\":\"bar\",\"fsk\":16}"))
                .andExpect(status().isOk())
                .andReturn();

        this.mvc.perform(
                post("/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"foo\",\"year\":2000,\"shortDescription\":\"bar\",\"fsk\":16}"))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(oldSize+1, movieRepository.count());
    
        movieRepository.delete(movieRepository.findById("foo").get());

        assertEquals(oldSize, movieRepository.count());
    }
}
