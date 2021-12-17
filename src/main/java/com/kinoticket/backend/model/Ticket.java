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
        private long id;

        @ManyToOne
        @NonNull
        private FilmShow filmShow;

        @OneToOne
        @NonNull
        private FilmShowSeat filmShowSeat;

        @Column
        @NonNull
        private double price;

        @OneToOne
        private Movie movie;

        @PrePersist
        public void setPriceForSeat(){
                if(filmShowSeat== null) return;
                switch (filmShowSeat.getSeat().getPriceCategory()){
                        case 1: this.price = 9.0D;break;
                        case 2: this.price = 12.0D;break;
                        case 3: this.price = 14.0D;break;
                        default: this.price = 15.0D; break;
                } }
}
