package com.kinoticket.backend.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.springframework.lang.NonNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    @NonNull
    private boolean isActive;

    @Column
    private Date created;

    @Column
    private Date updated;

    @Column
    @NonNull
    private long customerId;


    @OneToMany()
    private List<Ticket> tickets;

    @Column
    @NonNull
    private boolean isPaid;

    @Column
    @NonNull
    private String meansOfPayment;

    @PrePersist
    protected void onCreate(){
        created = new Date();
        updated = new Date();
    }

    public Booking(Long id, boolean isActive, int customerId) {
        this.id = id;
        this.isActive = isActive;
        this.customerId = customerId;
    }

    //to be finished in another ticket
}
