package com.kinoticket.backend.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import com.kinoticket.backend.UnitTestConfiguration;
import com.kinoticket.backend.model.Coupon;
import com.kinoticket.backend.repositories.CouponRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@Import(UnitTestConfiguration.class)
public class CouponControllerTest {

        @Autowired
        WebApplicationContext webApplicationContext;

        MockMvc mvc;

        @BeforeEach
        void before() {
            mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        }
        @MockBean
        CouponRepository couponRepository;

        @Test
        void getCoupon() throws Exception {

                when(couponRepository.findById(any())).thenReturn(Optional.of(new Coupon()));
                mvc.perform(get("/coupons/1"))
                                .andExpect(status().isOk());

                when(couponRepository.findById(any())).thenReturn(Optional.empty());
                mvc.perform(get("/coupons/1"))
                                .andExpect(status().isBadRequest());
        }
}
