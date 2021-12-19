package com.kinoticket.backend;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import com.kinoticket.backend.initData.CouponDataLoader;
import com.kinoticket.backend.model.Coupon;
import com.kinoticket.backend.repositories.CouponRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(UnitTestConfiguration.class)
public class CouponDataLoaderTest {

    @Autowired
    ApplicationArguments applicationArguments;

    @MockBean
    CouponRepository couponRepository;

    @Test
    void testCouponDataLoaderIfNoCouponsAreAvailable() throws IOException, ParseException {
        CouponDataLoader couponDataLoader = new CouponDataLoader(couponRepository);
        when(couponRepository.findAll()).thenReturn(new ArrayList<Coupon>());
        when(couponRepository.saveAll(any())).thenReturn(null);
        couponDataLoader.run(applicationArguments);
    }
}
