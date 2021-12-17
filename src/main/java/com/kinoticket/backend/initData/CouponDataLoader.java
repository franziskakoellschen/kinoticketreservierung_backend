package com.kinoticket.backend.initData;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.kinoticket.backend.model.Coupon;
import com.kinoticket.backend.repositories.CouponRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class CouponDataLoader implements ApplicationRunner {

    private CouponRepository couponRepository;

    @Autowired
    public CouponDataLoader(
           CouponRepository couponRepository
    ) {
        this.couponRepository = couponRepository;
    }

    public void run(ApplicationArguments args) throws IOException, ParseException {
        List<Coupon> couponList = new ArrayList<>();
        couponList = (List<Coupon>) couponRepository.findAll();

        if(couponList.size() ==0){
            Coupon coupon1 = new Coupon(29382952028l, 5.0);
            Coupon coupon2 = new Coupon(29382952021l, 5.0);
            Coupon coupon3 = new Coupon(29382952628l, 4.0);
            Coupon coupon4 = new Coupon(29382962028l, 3.0);

            couponList.add(coupon1);
            couponList.add(coupon2);
            couponList.add(coupon3);
            couponList.add(coupon4);

            couponRepository.saveAll(couponList);

        }
    }


}
