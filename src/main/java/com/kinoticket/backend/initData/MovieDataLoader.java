package com.kinoticket.backend.initData;

import java.io.IOException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.kinoticket.backend.model.CinemaHall;
import com.kinoticket.backend.model.Movie;
import com.kinoticket.backend.model.Seat;
import com.kinoticket.backend.repositories.MovieRepository;
import com.kinoticket.backend.service.CinemaHallService;
import com.kinoticket.backend.service.FilmShowService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class MovieDataLoader implements ApplicationRunner {

    private MovieRepository movieRepository;
    private FilmShowService filmShowService;
    private CinemaHallService cinemaHallService;

    @Autowired
    public MovieDataLoader(
        MovieRepository movieRepository,
        FilmShowService filmShowService,
        CinemaHallService cinemaHallService
    ) {
        this.movieRepository = movieRepository;
        this.filmShowService = filmShowService;
        this.cinemaHallService = cinemaHallService;
    }

    public void run(ApplicationArguments args) throws IOException, ParseException {

        for (Movie m : movieRepository.findAll()) {
            if (m.getTitle().equals("Test Movie")) {
                return;
            }
        }

        Movie m = new Movie();

        m.setTitle("Test Movie");
        m.setYear(2000);
        m.setShortDescription("Jahrzehnte umspannendes Drama um Intrigen und Machtspiele, Luxus und Dekadenz innerhalb der Familie hinter dem Modeimperium.");
        m.setDescription("Maurizio Gucci, verwöhnter und verschwenderischer Enkel des Gründers der berühmten Modemarke, heiratet 1972 die ehrgeizige Patricia Reggiani und residiert mit ihr in New York. Nach dem Tod des Vaters kämpft er mit allen Mitteln um die Vorherrschaft im Imperium und trennt sich von Patricia. Die will sich mit dieser Abfuhr nicht zufrieden geben und kämpft ihrerseits um weiteren Einfluss und Abfindung.");
        m.setFsk(16);
        m.setTrailer("https://www.youtube.com/watch?v=pGi3Bgn7U5U");
        m.setImageUrl("someImageUrl");
        m.setActors("Peter Pan, Rafael Martin, Gregor Münker");
        m.setStartDate(new Date());
        m.setFilmLength(221);
        m.setOriginCountry("USA");
        m.setGenre("Comedy");
        m.setImage_id(99l);
        m.setTemplateUrl("https://img.welt.de/img/icon/news/mobile235403144/4322500277-ci102l-w1024/2021-House-of-Gucci-Movie-Set.jpg");

        movieRepository.save(m);

        CinemaHall cinemaHall = createCinemaHall(5, 6, 20, 20);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1  = dateFormat.parse("2022-01-01 00:00:00");
        Date date2  = dateFormat.parse("2022-01-03 00:00:00");
        Date date3  = dateFormat.parse("2022-01-05 00:00:00");

        filmShowService.postFilmShow(date1, new Time(2,4,5), m.getId(), cinemaHall.getId());
        filmShowService.postFilmShow(date2,     new Time(2,4,5), m.getId(), cinemaHall.getId());
        filmShowService.postFilmShow(date3, new Time(2,4,5), m.getId(), cinemaHall.getId());
    }

    private CinemaHall createCinemaHall(int rows, int seatsPerRow, int squareMeters, int screenSize) {
        CinemaHall createdHall = cinemaHallService.addCinemaHall(squareMeters, screenSize);

        ArrayList<Seat> seats = new ArrayList<>();

        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= seatsPerRow; j++) {
                Seat s = new Seat();
                s.setPriceCategory(1);
                s.setRow(i);
                s.setSeatNumber(j);
                seats.add(s);
            }
        }

        return cinemaHallService.putSeats(seats, createdHall);
    }
}
