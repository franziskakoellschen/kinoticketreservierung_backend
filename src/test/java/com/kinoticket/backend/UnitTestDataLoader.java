package com.kinoticket.backend;

import com.kinoticket.backend.initData.CouponDataLoader;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.stereotype.Component;

@Component
public class UnitTestDataLoader {

    @MockBean
    public CouponDataLoader couponDataLoader;
}
