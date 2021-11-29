package com.kinoticket.backend.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kinoticket.backend.model.Booking;
import com.kinoticket.backend.model.Movie;
import com.kinoticket.backend.model.Ticket;
import com.kinoticket.backend.repositories.BookingRepository;
import com.kinoticket.backend.rest.BookingController;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@AutoConfigureMockMvc
public class BookingServiceTests {


    @Autowired
    WebApplicationContext webApplicationContext;

    @InjectMocks
    private BookingService bookingService;

    @InjectMocks
    private BookingController bookingController;

    @MockBean
    BookingRepository bookingRepository;

    private JacksonTester<Booking> jsonBooking;

    JacksonTester<List<Booking>> jsonBookingList;


    @Autowired
    BookingRepository repository;

    MockMvc mvc;

    @BeforeEach
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    public Booking createBooking(){
        Booking booking = new Booking();

        String meansOfPayment = "Visa";
        boolean isPaid = true;
        boolean isActive = true;
        long customerID = 344646l;
        
        Ticket ticket = new Ticket();

        String filmShowId = "53252";
        String seat ="5b";
        double price= 10.2;

        Movie movie = new Movie();

        String title = "Test Movie";
        int year = 2000;
        int fsk = 12;
        String shortDescription = "This is a Description";
        String description = "This is a full Description";
        String trailer = "someTrailerUrl";

        movie.setTitle(title);
        movie.setYear(year);
        movie.setFsk(fsk);
        movie.setShortDescription(shortDescription);
        movie.setDescription(description);
        movie.setTrailer(trailer);

        ticket.setFilmShowID(filmShowId);
        ticket.setMovie(movie);
        ticket.setSeat(seat);
        ticket.setPrice(price);
        List<Ticket> ticketList = new ArrayList<>();
        ticketList.add(ticket);

        booking.setMeansOfPayment(meansOfPayment);
        booking.setPaid(isPaid);
        booking.setActive(isActive);
        booking.setCustomerId(customerID);
        booking.setTickets(ticketList);

        return booking;
    }

    @Test
    void canRetrieveByIdWhenExists() throws Exception {

        Booking booking = createBooking();
        booking.setId(1l);

        when(bookingRepository.findById(1l)).thenReturn(booking);
        MockHttpServletResponse response = mvc.perform(get("/booking/1")
            .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();


        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo( jsonBooking.write(booking).getJson());
    }

    @Test
    public void canRetrieveByIdWhenDoesNotExist() throws Exception{

        when(bookingRepository.getById(1l)).thenReturn(null);

        MockHttpServletResponse response = mvc.perform(get("/booking/1")
                .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).isEmpty();

    }

@Test
    public void canCreateNewBooking() throws Exception{
        // when
        MockHttpServletResponse response = mvc.perform(
                post("/booking/").contentType(MediaType.APPLICATION_JSON).content(
                        jsonBooking.write(createBooking()).getJson()
                )).andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    public void testUpdateBooking() throws Exception {

        Booking createdBooking = createBooking();
        // when
         mvc.perform(
                post("/booking/").contentType(MediaType.APPLICATION_JSON).content(
                        jsonBooking.write(createdBooking).getJson()
                )).andReturn().getResponse();

        // when
        MockHttpServletResponse response = mvc.perform(
                post("/booking/").contentType(MediaType.APPLICATION_JSON).content(
                        jsonBooking.write(createdBooking).getJson()
                )).andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void canRetrieveByCustomerIdWhenExists() throws Exception {

        when(bookingRepository.findByCustomerId(1)).thenReturn(null);

        MockHttpServletResponse response = mvc.perform(get("/booking/customerId/1")
                .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).isEqualTo("");
    }

    @Test
    void canRetrieveByTimestamp() throws Exception {

        List<Booking> bookings = new ArrayList<>();
        Booking b = createBooking();
        

        String firstDate = "7-Jun-2013";
        String secondDate = "7-Jun-2022";


        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        b.setCreated(formatter.parse(firstDate));
        b.setUpdated(formatter.parse(secondDate));

        bookings.add(b);

        when(bookingRepository.findAllByCreatedBetween(
            formatter.parse(firstDate), formatter.parse(secondDate)
        )).thenReturn(bookings);

        MockHttpServletResponse response = mvc.perform(get("/booking/btw/7-Jun-2013/7-Jun-2022")
                .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        assertThat(response.getContentAsString())
            .contains("\"created\":\"2013-06-06T22:00:00.000+00:00\",\"updated\":\"2022-06-06T22:00:00.000+00:00\"");

    }

}
