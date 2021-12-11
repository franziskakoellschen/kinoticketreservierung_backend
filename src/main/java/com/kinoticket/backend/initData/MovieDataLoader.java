package com.kinoticket.backend.initData;

import com.kinoticket.backend.model.FilmShow;
import com.kinoticket.backend.model.Movie;
import com.kinoticket.backend.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class MovieDataLoader implements ApplicationRunner {

    private MovieRepository movieRepository;

    @Autowired
    public MovieDataLoader(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public void run(ApplicationArguments args) throws IOException {
        List<FilmShow> filmShowList = new ArrayList<>();
        filmShowList.add(new FilmShow(1l, new Date(), new Time(2,4,5),null));

        movieRepository.save(new Movie(15, "Test Movie",
                2000,
                "Jahrzehnte umspannendes Drama um Intrigen und Machtspiele, Luxus und Dekadenz innerhalb der Familie hinter dem Modeimperium.",
                "Maurizio Gucci, verwöhnter und verschwenderischer Enkel des Gründers der berühmten Modemarke, heiratet 1972 die ehrgeizige Patricia Reggiani und residiert mit ihr in New York. Nach dem Tod des Vaters kämpft er mit allen Mitteln um die Vorherrschaft im Imperium und trennt sich von Patricia. Die will sich mit dieser Abfuhr nicht zufrieden geben und kämpft ihrerseits um weiteren Einfluss und Abfindung.",
                16, "https://www.youtube.com/watch?v=pGi3Bgn7U5U", filmShowList, "someImageUrl", "Peter Pan, Rafael Martin, Gregor Münker" , new Date() , 221 , "USA" , "Comedy", 2l));
    }
}