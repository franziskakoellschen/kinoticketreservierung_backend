package com.kinoticket.backend.model;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CouponTest {
    @Test
    void testCoupon() {
        Coupon coupon = new Coupon(1L, 0.5);
        assertTrue(coupon.isActive());
    }
}
