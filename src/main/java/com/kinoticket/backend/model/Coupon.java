package com.kinoticket.backend.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@Data
public class Coupon {

    @Id
    Long id;

    @Column
    double discount;

    @Column
    boolean isActive;

    public Coupon(Long id, double discount) {
        this.id = id;
        this.discount = discount;
        this.isActive = true;
    }
}