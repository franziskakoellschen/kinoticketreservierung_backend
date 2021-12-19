package com.kinoticket.backend.rest;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kinoticket.backend.UnitTestConfiguration;
import com.kinoticket.backend.model.Movie;
import com.kinoticket.backend.repositories.FilmShowRepository;
import com.kinoticket.backend.repositories.MovieRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@Import(UnitTestConfiguration.class)
public class MoviesControllerTests {

    @Autowired
    WebApplicationContext webApplicationContext;

    @MockBean
    MovieRepository movieRepository;

    @MockBean
    FilmShowRepository filmShowRepository;

    MockMvc mvc;

    private JacksonTester<Movie> jsonMovie;

    @BeforeEach
    void before() {
        JacksonTester.initFields(this, new ObjectMapper());
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    Movie createMovie() {
        Movie movie = new Movie();

        movie.setTitle("Foo");
        movie.setYear(2000);
        movie.setShortDescription("FooBar");
        movie.setDescription("This is the long description");
        movie.setFsk(16);

        return movie;
    }

    @Test
    void testGetAll() throws Exception {

        ArrayList<Movie> movies = new ArrayList<Movie>();
        Movie movieA = new Movie();
        movieA.setId(4711);
        movies.add(movieA);
        Movie movieB = new Movie();
        movieB.setId(9876);
        movies.add(movieB);

        when(movieRepository.findAll()).thenReturn(movies);
        MvcResult result = this.mvc.perform(get("/movies"))
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("\"id\":4711"));
        assertTrue(result.getResponse().getContentAsString().contains("\"id\":9876"));
    }

    @Test
    void testPost() throws Exception {

        when(movieRepository.save(any())).thenReturn(createMovie());
        MvcResult result = this.mvc.perform(
                post("/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMovie.write(createMovie()).getJson()))
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("title"));
        assertTrue(result.getResponse().getContentAsString().contains("Foo"));
        assertTrue(result.getResponse().getContentAsString().contains("year"));
        assertTrue(result.getResponse().getContentAsString().contains("2000"));
        assertTrue(result.getResponse().getContentAsString().contains("shortDescription"));
        assertTrue(result.getResponse().getContentAsString().contains("FooBar"));
        assertTrue(result.getResponse().getContentAsString().contains("fsk"));
        assertTrue(result.getResponse().getContentAsString().contains("16"));
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
    }
}
