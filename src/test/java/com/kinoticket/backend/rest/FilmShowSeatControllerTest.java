package com.kinoticket.backend.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kinoticket.backend.model.Booking;
import com.kinoticket.backend.model.FilmShow;
import com.kinoticket.backend.model.FilmShowSeat;
import com.kinoticket.backend.model.Seat;
import com.kinoticket.backend.repositories.FilmShowRepository;
import com.kinoticket.backend.repositories.FilmShowSeatRepository;
import com.kinoticket.backend.repositories.SeatsRepository;
import com.kinoticket.backend.service.BookingService;
import com.kinoticket.backend.service.FilmShowSeatService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class FilmShowSeatControllerTest {

    @Autowired
    WebApplicationContext webApplicationContext;

    @InjectMocks
    private FilmShowSeatService filmShowSeatService;

    @InjectMocks
    private FilmShowSeatController filmShowSeatController;

    @MockBean
    FilmShowSeatRepository filmShowSeatRepository;

    @MockBean
    SeatsRepository seatsRepository;

    @MockBean
    FilmShowRepository filmShowRepository;

    private JacksonTester<FilmShowSeat> filmShowSeatJacksonTester;

    MockMvc mvc;

    @BeforeEach
    void before() {
        JacksonTester.initFields(this, new ObjectMapper());
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

        Seat seat = new Seat();
        seat.setId(2000);

        FilmShow filmShow = new FilmShow();
        filmShow.setId(1000);

        FilmShowSeat filmShowSeat = new FilmShowSeat(seat, filmShow, false);

        when(filmShowSeatRepository.findBySeat_idAndFilmShow_id(2000, 1000)).thenReturn(java.util.Optional.of(filmShowSeat));

        MockHttpServletResponse response = this.mvc.perform(put("/filmshows/1000/seats/2000/reserved/true")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        assertThat(response.getContentAsString()).isEqualTo( filmShowSeatJacksonTester.write(filmShowSeat).getJson());
    }

}
