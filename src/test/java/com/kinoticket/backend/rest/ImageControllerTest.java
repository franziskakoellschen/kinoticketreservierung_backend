package com.kinoticket.backend.rest;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.kinoticket.backend.initData.ImageDataLoader;
import com.kinoticket.backend.model.Image;
import com.kinoticket.backend.model.Movie;
import com.kinoticket.backend.repositories.FilmShowRepository;
import com.kinoticket.backend.repositories.ImageDbRepository;
import com.kinoticket.backend.repositories.MovieRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@SpringBootTest
public class ImageControllerTest{

    @Autowired
    WebApplicationContext webApplicationContext;

    @MockBean
    ImageDbRepository imageDbRepository;



    MockMvc mvc;


    private JacksonTester<Image> jsonMovie;


    @BeforeEach
    void before() {
        JacksonTester.initFields(this, new ObjectMapper());
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    Image createImage() throws IOException {
      Image image = ImageDataLoader.getImage().get(0);
      return image;
    }

    @Test
    void testGetImage() throws Exception {

        Image image = createImage();
        image.setId(2l);
        byte[] byteImage = image.getContent();

        when(imageDbRepository.findById(any())).thenReturn(Optional.of((image)));
        MvcResult result = this.mvc.perform(get("/image/"+
                image.getId()))
                .andExpect(status().isOk())
                .andReturn();


        String testString = new String(byteImage);
        String testString2 = new String(result.getResponse().getContentAsByteArray());


        assertEquals(testString2,testString);

    }

}
