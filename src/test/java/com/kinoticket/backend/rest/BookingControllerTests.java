package com.kinoticket.backend.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Iterator;

import com.kinoticket.backend.model.Booking;
import com.kinoticket.backend.model.Ticket;
import com.kinoticket.backend.repositories.BookingRepository;
import com.kinoticket.backend.repositories.MovieRepository;
import com.kinoticket.backend.repositories.TicketRepository;

@SpringBootTest
public class BookingControllerTests {

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    BookingRepository repository;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    TicketRepository ticketRepository;

    MockMvc mvc;

    @BeforeEach
    void before() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        Iterator<Booking> iterator = repository.findAll().iterator();
        while(iterator.hasNext()) {
            Booking b = iterator.next();
            if (b.getCustomerId() == 12345) {
                repository.deleteById(b.getId());
            }
        }

        this.mvc.perform(
                post("/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"foo\",\"year\":2000,\"shortDescription\":\"bar\",\"fsk\":16}"))
                .andExpect(status().isOk());

        
        if (movieRepository.findById("foo").isPresent()) {
            movieRepository.delete(movieRepository.findById("foo").get());
        }
    }

    @AfterEach
    void after() {
        
        Iterator<Booking> bIterator= repository.findAll().iterator();
        while(bIterator.hasNext()) {
            Booking b = bIterator.next();
            if (b.getCustomerId() == 12345) {
                repository.deleteById(b.getId());
            }
        }

        Iterator<Ticket> tIterator = ticketRepository.findAll().iterator();
        while(tIterator.hasNext()) {
            Ticket ticket = tIterator.next();
            if(ticket.getMovie().getTitle().equals("foo")) {
                ticketRepository.delete(ticket);
            }
        }
        
        if (movieRepository.findById("foo").isPresent()) {
            movieRepository.delete(movieRepository.findById("foo").get());
        }
    }

    @Test
    void testGetBookings() throws Exception {
        this.mvc.perform(get("/booking"))
            .andExpect(status().isOk());
    }

    @Test
    void testPostAndGetBooking() throws Exception {

        long oldSize = repository.count();

        this.mvc.perform(
                post("/booking")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"isActive\":\"true\", \"customerId\":12345, \"tickets\":[{\"movie\":{\"title\":\"foo\"}}]}")
            )
            .andExpect(status().isOk());

        assertEquals(repository.count(), oldSize+1);

        long id = -1;

        Iterator<Booking> iterator = repository.findAll().iterator();
        while(iterator.hasNext()) {
            Booking b = iterator.next();
            if (b.getCustomerId() == 12345) {
                id = b.getId();
                
                assertNotNull(repository.getById(id).getCreated());
                
                this.mvc.perform(
                        get("/booking/"+id))
                    .andExpect(status().isOk());

                long ticketId = repository.getById(id).getTickets().get(0).getId();
                repository.deleteById(id);
                ticketRepository.deleteById(ticketId);

                assertEquals(repository.count(), oldSize);
            }
        }

        assertNotEquals(id, -1);
    }

    @Test
    void testUpdateBooking() throws Exception {
        long oldSize = repository.count();

        this.mvc.perform(
                post("/booking")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"isActive\":\"true\", \"customerId\":12345, \"tickets\":[{\"movie\":{\"title\":\"foo\"}}]}")
            )
            .andExpect(status().isOk());

        long id = -1;

        Iterator<Booking> iterator = repository.findAll().iterator();
        while(iterator.hasNext()) {
            Booking b = iterator.next();
            if (b.getCustomerId() == 12345) {
                id = b.getId();

                this.mvc.perform(
                    put("/booking/"+id+"/cancel"))
                    .andExpect(status().isOk());

                assertFalse(repository.findById(id).isActive());

                long ticketId = repository.getById(id).getTickets().get(0).getId();
                repository.deleteById(id);
                ticketRepository.deleteById(ticketId);

                assertEquals(repository.count(), oldSize);
            }
        }

        assertNotEquals(id, -1);
    }
}
