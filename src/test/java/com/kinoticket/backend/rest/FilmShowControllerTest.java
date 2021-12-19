package com.kinoticket.backend.rest;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kinoticket.backend.UnitTestConfiguration;
import com.kinoticket.backend.model.*;
import com.kinoticket.backend.repositories.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@Import(UnitTestConfiguration.class)
public class FilmShowControllerTest {

    @Autowired
    WebApplicationContext webApplicationContext;

    @MockBean
    FilmShowRepository filmShowRepository;

    @MockBean
    MovieRepository movieRepository;

    @MockBean
    SeatsRepository seatsRepository;

    @MockBean
    FilmShowSeatRepository filmShowSeatRepository;

    @MockBean
    CinemaHallRepository cinemaHallRepository;

    private JacksonTester<FilmShowInformationDTO> filmShowSeatJacksonTester;

    MockMvc mvc;

    @BeforeEach
    void before() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @Test
    void testPostFilmshow() throws Exception {

        CinemaHall c = new CinemaHall();
        c.setId(1);

        Seat s1 = new Seat();
        s1.setId(1);
        s1.setRow(1);
        s1.setSeatNumber(1);
        Seat s2 = new Seat();
        s2.setId(2);
        s2.setRow(1);
        s2.setSeatNumber(2);
        Seat s3 = new Seat();
        s1.setId(3);
        s1.setRow(2);
        s1.setSeatNumber(1);

        ArrayList<Seat> seats = new ArrayList<Seat>();
        seats.add(s1);
        seats.add(s2);
        seats.add(s3);
        c.setSeats(seats);

        when(movieRepository.findById(2L)).thenReturn(Optional.of(new Movie()));
        when(cinemaHallRepository.findById(1L)).thenReturn(Optional.of(c));
        this.mvc.perform(post("/filmshows")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"date\":\"2021-12-13\",\"time\":\"09:00:00\",\"movieId\": 2,\"cinemaHallId\": 1}"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void getAllFilmShows() throws Exception {
        this.mvc.perform(get("/filmshows"))
                .andExpect(status().isOk());
    }

    @Test
    void testPostFilmshowNullBody() throws Exception {

        this.mvc.perform(post("/filmshows")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{  }"))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void getFilmShowInformationDTODoesNotCrash() throws Exception {

        this.mvc.perform(get("/filmshows/9999")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testFilmShowSeatsArePresent() throws Exception {

        FilmShow fs = new FilmShow();
        fs.setId(1000);
        Seat s1 = new Seat();
        s1.setId(2);
        s1.setRow(2);
        s1.setSeatNumber(4);
        FilmShowSeat testSeat1 = new FilmShowSeat(s1, fs);
        Movie m = new Movie();
        m.setTitle("Test Movie");
        fs.setMovie(m);
        CinemaHall ch = new CinemaHall();
        ch.setId(7654);
        fs.setCinemaHall(ch);

        Seat s2 = new Seat();
        s2.setId(2);
        s2.setRow(1);
        s2.setSeatNumber(2);
        FilmShowSeat testSeat2 = new FilmShowSeat(s2, fs);

        Seat s3 = new Seat();
        s3.setId(2);
        s3.setRow(1);
        s3.setSeatNumber(1);
        FilmShowSeat testSeat3 = new FilmShowSeat(s3, fs);

        ArrayList<FilmShowSeat> seats = new ArrayList<>();
        seats.add(testSeat1);
        seats.add(testSeat2);
        seats.add(testSeat3);

        when(filmShowSeatRepository.findByFilmShow_id(1000)).thenReturn(seats);
        when(filmShowRepository.findById(1000L)).thenReturn(Optional.of(fs));
        MockHttpServletResponse response = this.mvc.perform(
                get("/filmshows/1000"))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        FilmShowInformationDTO parseObject = filmShowSeatJacksonTester.parseObject(response.getContentAsString());

        assertTrue(parseObject.getFilmShowSeats().size() == 2);
        assertTrue(parseObject.getFilmShowSeats().get(0).size() == 2);
        assertTrue(parseObject.getFilmShowSeats().get(1).size() == 1);

        assertTrue(parseObject.getFilmShowSeats().get(0).get(0).getSeat().getSeatNumber() == 1);
        assertTrue(parseObject.getFilmShowSeats().get(0).get(1).getSeat().getSeatNumber() == 2);
        assertTrue(parseObject.getFilmShowSeats().get(1).get(0).getSeat().getSeatNumber() == 4);

        assertTrue(parseObject.getFilmShowSeats().get(0).get(0).getStatus() == FilmShowSeatStatus.FREE);

        assertTrue(parseObject.getMovie().getTitle().equals("Test Movie"));
        assertTrue(parseObject.getCinemaHall().getId() == 7654);
    }
}
