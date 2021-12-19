package com.kinoticket.backend.model;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

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


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private BookingAddress bookingAddress;

    @Column
    @NonNull
    private String meansOfPayment;

    private double totalSum;

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
