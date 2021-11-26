package com.kinoticket.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import javax.persistence.*;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Ticket")
public class Ticket {


        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Id
        private int id;

        @Column
        @NonNull
        private String filmShowID;


        @Column
        @NonNull
        private String seat;

        @Column
        @NonNull
        private double price;

        @OneToOne
        private Movie movie;


}
