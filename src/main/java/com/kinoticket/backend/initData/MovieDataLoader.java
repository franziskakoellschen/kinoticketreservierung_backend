package com.kinoticket.backend.initData;

import java.io.IOException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.kinoticket.backend.model.CinemaHall;
import com.kinoticket.backend.model.Movie;
import com.kinoticket.backend.repositories.CinemaHallRepository;
import com.kinoticket.backend.repositories.MovieRepository;
import com.kinoticket.backend.service.FilmShowService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class MovieDataLoader implements ApplicationRunner {

    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private FilmShowService filmShowService;
    @Autowired
    private CinemaHallRepository chr;

    public void run(ApplicationArguments args) throws IOException, ParseException {

        Iterable<Movie> findAll = movieRepository.findAll();
        for (Movie movie : findAll) {
            if (movie.getActors().equals("Peter Pan, Rafael Martin, Gregor Münker")) {
                movieRepository.delete(movie);
            }
        }
        Iterable<CinemaHall> cHalls = chr.findAll();
        for (CinemaHall cHall : cHalls) {
            if (cHall.getScreenSize()==-1 && cHall.getSquareMeters()==-1) {
                chr.delete(cHall);
            }
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date result  = dateFormat.parse("2021-12-15 00:00:00");

        CinemaHall ch = new CinemaHall();
        ch.setScreenSize(-1);
        ch.setSquareMeters(-1);

        chr.save(ch);

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
        m.setImage_id(991);

        movieRepository.save(m);

        filmShowService.postFilmShow(new Date(), new Time(2,4,5), m.getId(), ch.getId());
        filmShowService.postFilmShow(result, new Time(2,4,5), m.getId(), ch.getId());
        filmShowService.postFilmShow(new Date(), new Time(2,4,5), m.getId(), ch.getId());
    }
}