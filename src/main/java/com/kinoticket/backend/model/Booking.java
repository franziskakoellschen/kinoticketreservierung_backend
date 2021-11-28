package com.kinoticket.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jdk.jfr.DataAmount;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
    @NonNull
    private int customerId;

    @OneToMany
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
    }


//to be finished in another ticket
}
