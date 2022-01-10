package com.kinoticket.backend.rest;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.kinoticket.backend.UnitTestConfiguration;
import com.kinoticket.backend.model.FilmShow;
import com.kinoticket.backend.model.FilterDTO;
import com.kinoticket.backend.model.Movie;
import com.kinoticket.backend.repositories.FilmShowRepository;
import com.kinoticket.backend.repositories.MovieRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
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

    public FilmShow createFilmShow(Movie movie) throws ParseException {
        FilmShow filmShow = new FilmShow();
        filmShow.setMovie(movie);

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);

        String dateInString = "7-Jan-2022";
        Date date = formatter.parse(dateInString);

        filmShow.setDate(date);
        filmShow.setTime(new Time(00,00,00));
        filmShow.setLanguage("DE");
        filmShow.setDimension("2D");

        return filmShow;

    }

    @Test
    void testFilterWithTwoDates() throws Exception {

        FilterDTO filterDTO = new FilterDTO();

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);

        String dateInString = "7-Jan-2022";
        Date date = formatter.parse(dateInString);

        filterDTO.setDate1(date);
        dateInString = "18-Jan-2022";
        Date date2 = formatter.parse(dateInString);
        filterDTO.setDate2(date2);
        filterDTO.setSearchString("Harry Potter");
        filterDTO.setLanguage("DE");
        filterDTO.setGenre("Comedy");
        filterDTO.setDimension("3D");

        List<Movie> movieList = new ArrayList<>();
        movieList.add(createMovie());

        FilmShow filmShow = createFilmShow(movieList.get(0));
        filmShow.setId(1l);

        List<FilmShow> filmShowList = new ArrayList<>();

        filmShowList.add(filmShow);

        when(movieRepository.findMovieWithFilters(any(), any())).thenReturn(movieList);
        when(filmShowRepository.findFilmShowsWithTwoDates(anyLong(),any() , any(), anyString(), anyString())).thenReturn(filmShowList);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(filterDTO);
        MvcResult result = this.mvc.perform( post("/movies/filters")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestJson))
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
    void testFilterWithOneDateFrom() throws Exception {

        FilterDTO filterDTO = new FilterDTO();

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);

        String dateInString = "7-Jan-2022";
        Date date = formatter.parse(dateInString);

        filterDTO.setDate1(date);
        filterDTO.setSearchString("Harry Potter");
        filterDTO.setLanguage("DE");
        filterDTO.setGenre("Comedy");
        filterDTO.setDimension("3D");

        java.sql.Date sqlDate1 = new java.sql.Date(date.getTime());

        List<Movie> movieList = new ArrayList<>();
        movieList.add(createMovie());

        FilmShow filmShow = createFilmShow(movieList.get(0));
        filmShow.setId(1l);

        List<FilmShow> filmShowList = new ArrayList<>();

        filmShowList.add(filmShow);

        when(movieRepository.findMovieWithFilters(any(), any())).thenReturn(movieList);

        when( filmShowRepository.findFilmShowsWithFromDate(anyLong(), any(), anyString(), anyString() )).thenReturn(filmShowList);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(filterDTO);
        MvcResult result = this.mvc.perform( post("/movies/filters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
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
    void testFilterWithOneDateTo() throws Exception {

        FilterDTO filterDTO = new FilterDTO();

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);

        String dateInString = "7-Jan-2022";
        Date date = formatter.parse(dateInString);

        dateInString = "18-Jan-2022";
        Date date2 = formatter.parse(dateInString);
        filterDTO.setDate2(date2);
        filterDTO.setSearchString("Harry Potter");
        filterDTO.setLanguage("DE");
        filterDTO.setGenre("Comedy");
        filterDTO.setDimension("3D");

        java.sql.Date sqlDate1 = new java.sql.Date(date.getTime());
        java.sql.Date sqlDate2 = new java.sql.Date(date2.getTime());

        List<Movie> movieList = new ArrayList<>();
        movieList.add(createMovie());

        FilmShow filmShow = createFilmShow(movieList.get(0));
        filmShow.setId(1l);

        List<FilmShow> filmShowList = new ArrayList<>();

        filmShowList.add(filmShow);

        when(movieRepository.findMovieWithFilters(any(), any())).thenReturn(movieList);

        when( filmShowRepository.findFilmShowsWithTwoDates(anyLong() ,any(),any(),anyString() , anyString() )).thenReturn(filmShowList);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(filterDTO);
        MvcResult result = this.mvc.perform( post("/movies/filters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
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
    void testFilterWithZeroDates() throws Exception {

        FilterDTO filterDTO = new FilterDTO();

        filterDTO.setSearchString("Harry Potter");
        filterDTO.setLanguage("DE");
        filterDTO.setGenre("Comedy");
        filterDTO.setDimension("3D");

        List<Movie> movieList = new ArrayList<>();
        movieList.add(createMovie());

        FilmShow filmShow = createFilmShow(movieList.get(0));
        filmShow.setId(1l);

        List<FilmShow> filmShowList = new ArrayList<>();

        filmShowList.add(filmShow);

        when(movieRepository.findMovieWithFilters(any(), any())).thenReturn(movieList);
        when(filmShowRepository.findFutureFilmShowsByMovieWithFilter(anyLong(),any() , any(), eq("3D"), eq("DE"))).thenReturn(filmShowList);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(filterDTO);
        MvcResult result = this.mvc.perform( post("/movies/filters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
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
