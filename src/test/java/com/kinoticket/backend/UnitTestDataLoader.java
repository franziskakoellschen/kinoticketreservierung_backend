package com.kinoticket.backend;

import com.kinoticket.backend.initData.CouponDataLoader;
import com.kinoticket.backend.initData.ImageDataLoader;
import com.kinoticket.backend.initData.MovieDataLoader;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.stereotype.Component;

@Component
public class UnitTestDataLoader {

    @MockBean
    public ImageDataLoader imageDataLoader;

    @MockBean
    public MovieDataLoader movieDataLoader;

    @MockBean
    public CouponDataLoader couponDataLoader;
}
