package com.kinoticket.backend.rest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kinoticket.backend.model.Booking;
import com.kinoticket.backend.model.FilmShow;
import com.kinoticket.backend.model.Movie;
import com.kinoticket.backend.model.Seat;
import com.kinoticket.backend.model.Ticket;
import com.kinoticket.backend.repositories.BookingRepository;
import com.kinoticket.backend.repositories.MovieRepository;
import com.kinoticket.backend.repositories.TicketRepository;
import com.kinoticket.backend.service.BookingService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@AutoConfigureMockMvc
public class BookingControllerTests {

    @Autowired
    WebApplicationContext webApplicationContext;

    @InjectMocks
    private BookingService bookingService;

    @InjectMocks
    private BookingController bookingController;

    @MockBean
    BookingRepository bookingRepository;

    @MockBean
    MovieRepository movieRepository;

    @MockBean
    TicketRepository ticketRepository;

    private JacksonTester<Booking> jsonBooking;

    JacksonTester<List<Booking>> jsonBookingList;

    MockMvc mvc;

    @BeforeEach
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    public Booking createBooking(){

      /*  Booking booking = new Booking();

        String meansOfPayment = "Visa";
        boolean isPaid = true;
        boolean isActive = true;
        long customerID = 344646l;
        
        Ticket ticket = new Ticket();

        FilmShow filmShow = new FilmShow();
        filmShow.setId(53252);
        
        Seat seat = new Seat();

        seat.setId(2);
        seat.setRow(5);
        seat.setSeatNumber(3);

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

        ticket.setFilmShow(filmShow);
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

    */
        return null;
    }

    @Test
    void getAllBookings() throws Exception {
/*
        when(bookingRepository.findAll()).thenReturn(new ArrayList<Booking>());
        mvc.perform(get("/booking"))
            .andExpect(status().isOk());
            */
    }

    @Test
    void canRetrieveByIdWhenExists() throws Exception {

     /*   Booking booking = createBooking();
        booking.setId(1l);

        when(bookingRepository.findById(1l)).thenReturn(booking);
        MockHttpServletResponse response = mvc.perform(get("/booking/1")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn().getResponse();

        assertThat(response.getContentAsString()).isEqualTo( jsonBooking.write(booking).getJson());
   */
    } }


    /*
    @Test
    public void postBookingWithMissingParameter() throws Exception{
        
        mvc.perform(
            post("/booking/").contentType(MediaType.APPLICATION_JSON).content(
                jsonBooking.write(new Booking()).getJson()
            )).andExpect(status().isBadRequest());

    }

    @Test
    public void canRetrieveByIdWhenDoesNotExist() throws Exception{

        when(bookingRepository.getById(1l)).thenReturn(null);

        MockHttpServletResponse response = mvc.perform(get("/booking/1")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andReturn().getResponse();

        assertThat(response.getContentAsString()).isEmpty();

    }

    @Test
    public void canCreateNewBooking() throws Exception{
        
        mvc.perform(
            post("/booking/").contentType(MediaType.APPLICATION_JSON).content(
                jsonBooking.write(createBooking()).getJson()
            )).andExpect(status().isOk());
    }

    @Test
    public void testPostSameBookingAgain() throws Exception {

        Booking createdBooking = createBooking();
        // when
         mvc.perform(
            post("/booking/").contentType(MediaType.APPLICATION_JSON).content(
                jsonBooking.write(createdBooking).getJson()
            ));

        // when
        mvc.perform(
            post("/booking/").contentType(MediaType.APPLICATION_JSON).content(
                jsonBooking.write(createdBooking).getJson()
            )).andExpect(status().isOk());

    }

    @Test
    public void updateBooking() throws Exception {

        Booking updatedBooking = createBooking();
        updatedBooking.setId(1l);
        updatedBooking.setPaid(false);

        when(bookingRepository.save(any())).thenReturn(updatedBooking);
        String contentAsString = mvc.perform(
                post("/booking/update")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonBooking.write(updatedBooking).getJson()))
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString();

        assertTrue(contentAsString.contains("\"paid\":false"));
    }

    @Test
    public void testPostBookingFromNullBody() throws Exception {

         mvc.perform(post("/booking")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{}"))
        .andExpect(status().isBadRequest());
        
    }

    
    @Test
    public void testUpdateBookingWithNullTickets() throws Exception {

        Booking booking = createBooking();
        booking.setTickets(null);

         mvc.perform(post("/booking/update")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonBooking.write(booking).getJson()))
        .andExpect(status().isBadRequest());
        
    }

    @Test
    public void testUpdateBookingWithNullId() throws Exception {

        Booking booking = createBooking();
        booking.setId(null);

         mvc.perform(post("/booking/update")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonBooking.write(booking).getJson()))
        .andExpect(status().isBadRequest());
        
    }

    @Test
    public void testUpdateBookingFromNullBody() throws Exception {

         mvc.perform(post("/booking/update")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{}"))
        .andExpect(status().isBadRequest());
        
    }

    @Test
    void canRetrieveByCustomerIdWhenNotExists() throws Exception {

        when(bookingRepository.findByCustomerId(1)).thenReturn(null);

        MockHttpServletResponse response = mvc.perform(get("/booking/customerId/1")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andReturn().getResponse();
        
        assertThat(response.getContentAsString()).isEqualTo("");
    }

    
    @Test
    void canRetrieveByCustomerIdWhenExists() throws Exception {

        List<Booking> bookings = new ArrayList<Booking>();
        bookings.add(createBooking());

        when(bookingRepository.findByCustomerId(1)).thenReturn(bookings);

        mvc.perform(get("/booking/customerId/1")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
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
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn().getResponse();

        assertThat(response.getContentAsString())
            .contains("\"created\":\"2013-06").contains("\"updated\":\"2022-06");
    }

    
    @Test
    void testWrongDateFormat() throws Exception {

        mvc.perform(get("/booking/btw/Juli 2013/Juli 2021")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());

    }

    @Test
    void cancelBooking() throws Exception {

        when(bookingRepository.findById(1l)).thenReturn(createBooking());
        mvc.perform(post("/booking/cancel/1"))
            .andExpect(status().isOk());

        when(bookingRepository.findById(1)).thenReturn(null);
        mvc.perform(post("/booking/cancel/1"))
            .andExpect(status().isBadRequest());
    }
}
*/